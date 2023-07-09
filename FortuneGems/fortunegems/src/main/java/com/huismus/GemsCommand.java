package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemsCommand implements CommandExecutor {
    private final GemInventoryManager inventoryManager;

    public GemsCommand(GemInventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gems")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                GemInventory gemInventory = inventoryManager.getGemInventory(player);
                if (gemInventory != null) {
                    player.openInventory(gemInventory.getInventory());
                } else {
                    sender.sendMessage(ChatColor.RED + "Gem inventory not found.");
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            }
        }
        return false;
    }
}
