package com.huismus;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private Location spawnLocation;

    public SpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.spawnLocation = plugin.getServer().getWorlds().get(0).getSpawnLocation(); // Default to world spawn
    }

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        player.teleport(spawnLocation);
        player.sendMessage("Teleported to spawn.");

        return true;
    }
}
