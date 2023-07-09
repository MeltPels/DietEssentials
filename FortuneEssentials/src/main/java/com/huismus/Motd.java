package com.huismus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

public class Motd {

    private final JavaPlugin plugin;

    public Motd(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getMotdFromFile(Player player) {
        // Define the path to MOTD.txt file
        File file = new File(plugin.getDataFolder(), "MOTD.txt");

        // Read the contents of MOTD.txt file
        StringBuilder motdBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("{PLAYER}", player.getName());
                line = line.replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motdBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to read MOTD.txt file: " + e.getMessage());
        }

        return motdBuilder.toString();
    }
}
