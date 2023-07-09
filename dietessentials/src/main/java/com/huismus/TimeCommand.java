package com.huismus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final Map<UUID, BukkitRunnable> timeTasks;

    public TimeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.timeTasks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String timeArg = args[0].toLowerCase();

            if (timeArg.equals("noon")) {
                transitionServerTime(6000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to noon.");
            } else if (timeArg.equals("morning")) {
                transitionServerTime(0);
                sender.sendMessage(ChatColor.YELLOW + "Time set to morning.");
            } else if (timeArg.equals("day")) {
                transitionServerTime(6000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to day.");
            } else if (timeArg.equals("evening")) {
                transitionServerTime(12000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to evening.");
            } else if (timeArg.equals("night")) {
                transitionServerTime(13000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to night.");
            } else if (timeArg.equals("midnight")) {
                transitionServerTime(18000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to midnight.");
            } else {
                sender.sendMessage(
                        ChatColor.RED + "Invalid time argument. Use: noon, morning, day, evening, night, midnight");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /time [noon, morning, day, evening, night, midnight]");
        }

        return true;
    }

    private void transitionServerTime(long targetTime) {
        
        new BukkitRunnable() {

            @Override
            public void run() {
                World world = plugin.getServer().getWorlds().get(0); // Assuming the first world represents the server's time
                long currentTime = world.getTime(); // Get the current in-game time in ticks
                long increment = currentTime < targetTime ? 100 : -100;

                if (currentTime < (targetTime + 100) && currentTime > (targetTime - 100)) {
                    setServerTime(targetTime);
                    cancel();
                    return;
                }

                long newServerTime = currentTime + increment;
                setServerTime(newServerTime);
            }
        }.runTaskTimer(plugin, 5L, 1L); // Run the task every tick
    }

    private void setServerTime(long time) {
        plugin.getServer().getWorlds().forEach(world -> world.setTime(time));
    }

}
