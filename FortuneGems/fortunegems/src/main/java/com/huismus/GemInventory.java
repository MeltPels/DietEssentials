package com.huismus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GemInventory implements ConfigurationSerializable {
    static final String INVENTORY_TITLE = "Gem Inventory";
    private final Map<GemType, Integer> gemCounts;
    private final Inventory inventory;

    public GemInventory() {
        this.gemCounts = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, 9, INVENTORY_TITLE);
        initializeInventory();
    }

    private void initializeInventory() {
        for (GemType gemType : GemType.values()) {
            ItemStack gemItem = gemType.createItemStack();
            inventory.addItem(gemItem);
        }
    }

    public void addGems(GemType gemType, int amount) {
        int currentAmount = gemCounts.getOrDefault(gemType, 0);
        gemCounts.put(gemType, currentAmount + amount);
        updateInventory(gemType);
    }

    public int getGemCount(GemType gemType) {
        return gemCounts.getOrDefault(gemType, 0);
    }

    public void removeGems(GemType gemType, int amount) {
        int currentAmount = gemCounts.getOrDefault(gemType, 0);
        int newAmount = Math.max(0, currentAmount - amount);
        gemCounts.put(gemType, newAmount);
        updateInventory(gemType);
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void updateInventory(GemType gemType) {
        ItemStack gemItem = gemType.createItemStack();
        int gemCount = gemCounts.getOrDefault(gemType, 0);
        gemItem.setAmount(gemCount);
        inventory.setItem(gemType.getSlot(), gemItem);
    }

    public void setGemCounts(Map<GemType, Integer> counts) {
        gemCounts.clear();
        gemCounts.putAll(counts);
        updateInventory();
    }

    public Map<GemType, Integer> getGemCounts() {
        return gemCounts;
    }

    private void updateInventory() {
        for (Map.Entry<GemType, Integer> entry : gemCounts.entrySet()) {
            GemType gemType = entry.getKey();
            int gemCount = entry.getValue();
            ItemStack gemItem = gemType.createItemStack();
            gemItem.setAmount(gemCount);
            inventory.setItem(gemType.getSlot(), gemItem);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("gemCounts", gemCounts);
        return data;
    }

    public static GemInventory deserialize(Map<String, Object> data) {
        GemInventory inventory = new GemInventory();
        if (data.containsKey("gemCounts")) {
            Object gemCountsObj = data.get("gemCounts");
            if (gemCountsObj instanceof Map) {
                Map<?, ?> gemCountsData = (Map<?, ?>) gemCountsObj;
                for (Map.Entry<?, ?> entry : gemCountsData.entrySet()) {
                    if (entry.getKey() instanceof String && entry.getValue() instanceof Integer) {
                        String gemTypeName = (String) entry.getKey();
                        int gemCount = (Integer) entry.getValue();
                        GemType gemType = GemType.fromString(gemTypeName);
                        if (gemType != null) {
                            inventory.gemCounts.put(gemType, gemCount);
                        }
                    }
                }
            }
        }
        return inventory;
    }
}
