package com.huismus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetSpawnCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final SpawnCommand spawnCommand;

    public SetSpawnCommand(JavaPlugin plugin, SpawnCommand spawnCommand) {
        this.plugin = plugin;
        this.spawnCommand = spawnCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        spawnCommand.setSpawnLocation(player.getLocation());
        player.sendMessage("Spawn location set.");

        return true;
    }
}
