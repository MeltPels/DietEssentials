package com.huismus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.ChatColor;

public class TimeCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public TimeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String timeArg = args[0].toLowerCase();

            World world = plugin.getServer().getWorlds().get(0); // Change to the appropriate world if needed

            if (timeArg.equals("noon")) {
                world.setTime(6000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Noon.");
            } else if (timeArg.equals("morning")) {
                world.setTime(0);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Morning.");
            } else if (timeArg.equals("day")) {
                world.setTime(1000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Day.");
            } else if (timeArg.equals("evening")) {
                world.setTime(12000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Evening.");
            } else if (timeArg.equals("night")) {
                world.setTime(13000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Night.");
            } else if (timeArg.equals("midnight")) {
                world.setTime(18000);
                sender.sendMessage(ChatColor.YELLOW + "Time set to Midnight.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid time argument. Use: noon, morning, day, evening, night or midnight");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /time [noon, morning, day, evening, night, midnight]");
        }

        return true;
    }
}
