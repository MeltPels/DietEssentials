package com.huismus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.UUID;

public class GemInventoryManager {
    private final Plugin plugin;
    private final FileConfiguration dataConfig;
    private final File dataFile;
    private final Map<Player, GemInventory> playerInventories;
    private final GemConfig gemConfig; // Add gemConfig field

    public GemInventoryManager(Plugin plugin, GemConfig gemConfig) { // Add gemConfig parameter to constructor
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "player_gems.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        this.playerInventories = new HashMap<>();
        this.gemConfig = gemConfig; // Initialize gemConfig field
    }

    public void addGems(Player player, GemType type, int amount) {
        GemInventory inventory = getGemInventory(player);
        inventory.addGems(type, amount);
        savePlayerGems(player);
    }

    public void removeGems(Player player, GemType type, int amount) {
        GemInventory inventory = getGemInventory(player);
        inventory.removeGems(type, amount);
        savePlayerGems(player);
    }

    public int getPlayerGemCount(Player player, GemType type) {
        GemInventory inventory = getGemInventory(player);
        return inventory.getGemCount(type);
    }

    public void loadPlayerGems(Player player) {
        UUID playerId = player.getUniqueId();
        Map<GemType, Integer> gemData = gemConfig.getPlayerGems(playerId.toString());
        if (!gemData.isEmpty()) {
            GemInventory inventory = getGemInventory(player);
            inventory.setGemCounts(gemData);
            playerInventories.put(player, inventory);
        }
    }

    public void savePlayerGems(Player player) {
        UUID playerId = player.getUniqueId();
        GemInventory inventory = getGemInventory(player);
        gemConfig.setPlayerGems(playerId.toString(), inventory.getGemCounts());
    }

    public Player getPlayer(String name) {
        return Bukkit.getPlayerExact(name);
    }

    public GemInventory getGemInventory(Player player) {
        GemInventory inventory = playerInventories.get(player);
        if (inventory == null) {
            inventory = new GemInventory();
            playerInventories.put(player, inventory);
        }
        return inventory;
    }
}
