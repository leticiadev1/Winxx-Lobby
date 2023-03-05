package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.FlyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FlyCommand extends FlyManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))return true;

        Player player=(Player)sender;
        FileConfiguration config = Terminal.getInstance().getConfig();

        if(command.getName().equalsIgnoreCase("fly")){
            if(player.hasPermission(config.getString("permissions.fly-use"))){
                String voando = isFlying(player) ? config.getString("fly-system.deactivated")
                        .replace("&", "§"):config.getString("fly-system.activated")
                        .replace("&", "§");

                player.setAllowFlight(isFlying(player) ? false : true);
                player.sendMessage(voando);
            }
        }

        return false;
    }
}
