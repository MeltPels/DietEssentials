package com.huismus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GemConfig {
    private final File gemStorage;
    private final JavaPlugin plugin;
    private YamlConfiguration config;

    public GemConfig(JavaPlugin plugin) {
        this.gemStorage = new File(plugin.getDataFolder(), "player_gems.yml");
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        if (!gemStorage.exists()) {
            plugin.saveResource("player_gems.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(gemStorage);
    }

    public void saveConfig() {
        try {
            config.save(gemStorage);
            plugin.getLogger().info("Saving gems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<GemType, Integer> getPlayerGems(String playerName) {
        ConfigurationSection playerSection = config.getConfigurationSection(playerName);
        if (playerSection != null) {
            Map<GemType, Integer> gemData = new HashMap<>();
            for (String gemTypeName : playerSection.getKeys(false)) {
                GemType gemType = GemType.fromString(gemTypeName);
                if (gemType != null) {
                    int amount = playerSection.getInt(gemTypeName);
                    gemData.put(gemType, amount);
                }
            }
            return gemData;
        }
        return new HashMap<>();
    }

    public void setPlayerGems(String playerName, Map<GemType, Integer> gemData) {
        ConfigurationSection playerSection = config.createSection(playerName);
        for (Map.Entry<GemType, Integer> entry : gemData.entrySet()) {
            String gemTypeName = entry.getKey().name();
            int amount = entry.getValue();
            playerSection.set(gemTypeName, amount);
        }
        saveConfig();
    }
}
