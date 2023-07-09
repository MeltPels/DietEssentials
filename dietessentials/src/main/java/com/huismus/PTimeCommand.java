package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PTimeCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final Map<UUID, BukkitRunnable> timeTasks;

    public PTimeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.timeTasks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            String timeArg = args[0].toLowerCase();

            if (timeArg.equals("noon")) {
                transitionPlayerTime(player, 6000);
                player.sendMessage(ChatColor.YELLOW + "Time set to noon.");
                return true;
            } else if (timeArg.equals("morning")) {
                transitionPlayerTime(player, 0);
                player.sendMessage(ChatColor.YELLOW + "Time set to morning.");
                return true;
            } else if (timeArg.equals("day")) {
                transitionPlayerTime(player, 8000);
                player.sendMessage(ChatColor.YELLOW + "Time set to day.");
                return true;
            } else if (timeArg.equals("evening")) {
                transitionPlayerTime(player, 12000);
                player.sendMessage(ChatColor.YELLOW + "Time set to evening.");
                return true;
            } else if (timeArg.equals("night")) {
                transitionPlayerTime(player, 13000);
                player.sendMessage(ChatColor.YELLOW + "Time set to night.");
                return true;
            } else if (timeArg.equals("midnight")) {
                transitionPlayerTime(player, 18000);
                player.sendMessage(ChatColor.YELLOW + "Time set to midnight.");
                return true;
            } else if (timeArg.equals("reset")) {
                cancelTimeTask(player.getUniqueId());
                player.resetPlayerTime();
                player.sendMessage(ChatColor.YELLOW + "Player time reset.");
                return true;
            }
        }

        player.sendMessage(ChatColor.RED + "Usage: /ptime [noon, morning, day, evening, night, midnight, reset]");
        return true;
    }

    private void transitionPlayerTime(Player player, long targetTime) {
        World world = plugin.getServer().getWorlds().get(0);
        long currentTime = world.getTime();
        long setTime = targetTime - currentTime;
        player.setPlayerTime(setTime, true);
    }

    private void cancelTimeTask(UUID playerId) {
        BukkitRunnable task = timeTasks.get(playerId);
        if (task != null) {
            task.cancel();
            timeTasks.remove(playerId);
        }
    }
}
