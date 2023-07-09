package com.huismus;

import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PWeatherCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public PWeatherCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String weatherArg = args[0].toLowerCase();

                if (weatherArg.equals("sun")) {
                    player.setPlayerWeather(WeatherType.CLEAR);
                    player.sendMessage(ChatColor.YELLOW + "Your weather set to sunny.");
                } else if (weatherArg.equals("rain")) {
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    player.sendMessage(ChatColor.YELLOW + "Your weather set to rainy.");
                } else if (weatherArg.equals("reset")) {
                    player.resetPlayerWeather();
                    player.sendMessage(ChatColor.YELLOW + "Your weather has been reset.");
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid weather argument. Use: sun, rain, reset");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /pweather [sun, rain, reset]");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
        }

        return true;
    }
}
