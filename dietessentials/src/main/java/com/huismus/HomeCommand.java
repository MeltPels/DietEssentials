package com.huismus;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final Map<UUID, Location> homes;
    private FileConfiguration dataConfig;
    private File dataFile;

    public HomeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.homes = new HashMap<>();
        this.dataFile = new File(plugin.getDataFolder(), "homes.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        // Load home locations from file
        loadHomes();
    }

    public void setHome(Player player, Location location) {
        homes.put(player.getUniqueId(), location);
        saveHomes();
        player.sendMessage("Home location set.");
    }

    public void teleportHome(Player player) {
        Location homeLocation = homes.get(player.getUniqueId());
        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendMessage("Teleported to home.");
        } else {
            player.sendMessage("&cYou haven't set a home location yet. Use /home set to set it!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            teleportHome(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
            setHome(player, player.getLocation());
        } else {
            player.sendMessage("Usage: /home [set]");
        }

        return true;
    }

    private void loadHomes() {
        if (dataFile.exists()) {
            ConfigurationSection homesSection = dataConfig.getConfigurationSection("homes");
            if (homesSection != null) {
                for (String playerId : homesSection.getKeys(false)) {
                    UUID uuid = UUID.fromString(playerId);
                    Location location = homesSection.getLocation(playerId);
                    homes.put(uuid, location);
                }
            }
        }
    }

    private void saveHomes() {
        ConfigurationSection homesSection = dataConfig.createSection("homes");
        for (Map.Entry<UUID, Location> entry : homes.entrySet()) {
            String playerId = entry.getKey().toString();
            Location location = entry.getValue();
            homesSection.set(playerId, location);
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save home data: " + e.getMessage());
        }
    }
}
