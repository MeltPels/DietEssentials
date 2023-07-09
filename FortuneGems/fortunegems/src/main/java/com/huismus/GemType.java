package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("GemType")
public enum GemType implements ConfigurationSerializable {
    FORTUNE(ChatColor.AQUA + "Fortune Gem", Material.DIAMOND, 0),
    CREATIVE(ChatColor.GOLD + "Creative Gem", Material.GOLD_INGOT, 1),
    EFFICIENCY(ChatColor.YELLOW + "Efficiency Gem", Material.IRON_INGOT, 2),
    ENDURANCE(ChatColor.GREEN + "Endurance Gem", Material.EMERALD, 3),
    STRENGTH(ChatColor.RED + "Strength Gem", Material.REDSTONE_BLOCK, 4),
    ENERGY(ChatColor.DARK_PURPLE + "Energy Gem", Material.AMETHYST_SHARD, 5);

    private String displayName;
    private Material material;
    private int slot;

    GemType(String displayName, Material material, int slot) {
        this.displayName = displayName;
        this.material = material;
        this.slot = slot;
    }

    static {
        ConfigurationSerialization.registerClass(GemType.class, "GemType");
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static GemType fromString(String name) {
        for (GemType gemType : values()) {
            if (gemType.name().equalsIgnoreCase(name)) {
                return gemType;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name());
        data.put("display_name", displayName);
        data.put("material", material.name());
        data.put("slot", slot);
        return data;
    }

    public static GemType deserialize(Map<String, Object> data) {
        String name = (String) data.get("name");
        String displayName = (String) data.get("display_name");
        Material material = Material.valueOf((String) data.get("material"));
        int slot = (int) data.get("slot");
        GemType gemType = GemType.valueOf(name);
        gemType.displayName = displayName;
        gemType.material = material;
        gemType.slot = slot;
        return gemType;
    }
}
