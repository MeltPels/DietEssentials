package com.huismus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FortuneGems extends JavaPlugin {
    private GemConfig gemConfig;
    private GemInventoryManager gemInventoryManager;

    @Override
    public void onEnable() {
        // Create GemConfig instance
        gemConfig = new GemConfig(this);
        gemConfig.loadConfig();

        // Create GemInventoryManager instance
        gemInventoryManager = new GemInventoryManager(this, gemConfig);

        // Register command executors
        getCommand("gems").setExecutor(new GemsCommand(gemInventoryManager));
        getCommand("reward").setExecutor(new RewardCommand(gemInventoryManager, gemConfig));

        // Load gems for online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            gemInventoryManager.loadPlayerGems(player);
        }
        // Other initialization logic
    }

    @Override
    public void onDisable() {
        // Save config on disable
        gemConfig.saveConfig();
    }
}
