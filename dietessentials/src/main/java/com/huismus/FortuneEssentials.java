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

public class FortuneEssentials extends JavaPlugin implements Listener {
    private Motd motd;
    private File folder = new File(getDataFolder().getParentFile(), "FortuneEssentials");

    @Override
    public void onEnable() {
        if (!folder.exists()) {
            init();
        }

         // Create instances of commandclasses
        SpawnCommand spawnCommand = new SpawnCommand(this);

        // Register the commands
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this, spawnCommand));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("time").setExecutor(new TimeCommand(this));
        getCommand("ptime").setExecutor(new PTimeCommand(this));
        getCommand("weather").setExecutor(new WeatherCommand(this));
        getCommand("pweather").setExecutor(new PWeatherCommand(this));

        getServer().getPluginManager().registerEvents(this, this);
        motd = new Motd(this); // Initialize the 'motd' field
    }

    public void init() {
        getLogger().info("First start of FortuneEssentials");

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
