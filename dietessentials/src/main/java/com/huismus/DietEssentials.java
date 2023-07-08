package com.huismus;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DietEssentials extends JavaPlugin implements Listener {
    private Motd motd;
    private File folder = new File(getDataFolder().getParentFile(), "DietEssentials");

    @Override
    public void onEnable() {
        if (!folder.exists()) {
            init();
        }

         // Create instances of commandclasses
        SpawnCommand spawnCommand = new SpawnCommand(this);
        SetSpawnCommand setSpawnCommand = new SetSpawnCommand(this, spawnCommand);
        HomeCommand homeCommand = new HomeCommand(this);
        TimeCommand timeCommand = new TimeCommand(this);

        // Register the commands
        getCommand("spawn").setExecutor(spawnCommand);
        getCommand("setspawn").setExecutor(setSpawnCommand);
        getCommand("home").setExecutor(homeCommand);
        getCommand("time").setExecutor(timeCommand);

        getServer().getPluginManager().registerEvents(this, this);
        motd = new Motd(this); // Initialize the 'motd' field
    }

    public void init() {
        getLogger().info("First start of DietEssentials");

        folder.mkdirs();
        File file = new File(getDataFolder(), "MOTD.txt");
        try {
            file.createNewFile();
            // Add initial content to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("Welcome to the server!");
                writer.println("Enjoy your stay!");
            }
        } catch (IOException e) {
            getLogger().warning("Failed to create MOTD.txt file: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get the joined player
        Player player = event.getPlayer();

        // Read MOTD from file
        String motdMessage = motd.getMotdFromFile(player);

        // Send a MOTD message to the player
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', motdMessage));
    }

    @Override
    public void onDisable() {
        getLogger().info("Huismus says goodbye!");
    }
}
