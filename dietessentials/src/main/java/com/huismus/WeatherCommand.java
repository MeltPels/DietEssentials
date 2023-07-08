package com.huismus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.ChatColor;

public class WeatherCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public WeatherCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String weatherArg = args[0].toLowerCase();
            int duration;

            try {
                duration = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid duration. Please enter a valid number.");
                return true;
            }

            World world = plugin.getServer().getWorlds().get(0); // Change to the appropriate world if needed

            if (weatherArg.equals("sun")) {
                world.setStorm(false);
                world.setThundering(false);
                sender.sendMessage(ChatColor.YELLOW + "Weather set to Sunny for " + duration + " seconds.");
            } else if (weatherArg.equals("rain")) {
                world.setStorm(true);
                world.setThundering(false);
                world.setWeatherDuration(duration * 20);
                sender.sendMessage(ChatColor.YELLOW + "Weather set to Rainy for " + duration + " seconds.");
            } else if (weatherArg.equals("storm")) {
                world.setStorm(true);
                world.setThundering(true);
                world.setWeatherDuration(duration * 20);
                sender.sendMessage(ChatColor.YELLOW + "Weather set to Stormy for " + duration + " seconds.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid weather argument. Use: sun, rain, storm");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /weather [sun, rain, storm] [duration in seconds]");
        }

        return true;
    }
}
