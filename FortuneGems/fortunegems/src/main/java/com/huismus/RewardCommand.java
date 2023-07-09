package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardCommand implements CommandExecutor {
    private final GemInventoryManager inventoryManager;
    private final GemConfig gemConfig;

    public RewardCommand(GemInventoryManager inventoryManager, GemConfig gemConfig) {
        this.inventoryManager = inventoryManager;
        this.gemConfig = gemConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reward")) {
            if (args.length == 3) {
                String playerName = args[0];
                Player player = inventoryManager.getPlayer(playerName);

                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                    return true;
                }

                int amount;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Invalid amount. Please enter a valid number.");
                    return true;
                }

                String gemType = args[2].toUpperCase();
                GemType type = GemType.fromString(gemType);

                if (type == null) {
                    sender.sendMessage(ChatColor.RED + "Invalid gem type.");
                    return true;
                }

                rewardPlayer(player, amount, type);
                sender.sendMessage(ChatColor.GREEN + "Successfully rewarded " + player.getName() + " with " + amount + " " + type.getDisplayName() + ".");
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /reward <player> <amount> <gemtype>");
            }
            return true;
        }
        return false;
    }

    private void rewardPlayer(Player player, int amount, GemType gemType) {
        inventoryManager.addGems(player, gemType, amount);
        inventoryManager.savePlayerGems(player); // Save player's gems
    }
}
