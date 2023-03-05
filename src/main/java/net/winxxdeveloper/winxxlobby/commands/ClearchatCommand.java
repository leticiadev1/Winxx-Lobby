package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ClearchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return true;

        Player player=(Player)sender;
        FileConfiguration config= Terminal.getInstance().getConfig();

        if(command.getName().equalsIgnoreCase("clearchat")){
            if(player.hasPermission(config.getString("permissions.admin"))){
                Bukkit.broadcastMessage(StringUtils.repeat(" \n", 200));
                Bukkit.broadcastMessage(" §aChat foi limpo por §f"+player.getDisplayName()+"§a.");
                Bukkit.broadcastMessage("");
            }
        }

        return false;
    }
}
