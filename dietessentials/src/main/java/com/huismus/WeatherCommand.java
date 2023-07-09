package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class WeatherCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public WeatherCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String weatherArg = args[0].toLowerCase();

            if (weatherArg.equals("sun")) {
                plugin.getServer().getWorlds().forEach(world -> world.setWeatherDuration(0));
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(false));
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Weather set to sunny.");
            } else if (weatherArg.equals("rain")) {
                plugin.getServer().getWorlds().forEach(world -> world.setWeatherDuration(0));
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(true));
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Weather set to rainy.");
            } else if (weatherArg.equals("storm")) {
                plugin.getServer().getWorlds().forEach(world -> world.setWeatherDuration(0));
                plugin.getServer().getWorlds().forEach(world -> world.setStorm(true));
                plugin.getServer().getWorlds().forEach(world -> world.setThundering(true));
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Weather set to stormy.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid weather argument. Use: sun, rain, storm");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /weather [sun, rain, storm]");
        }

        return true;
    }
}
