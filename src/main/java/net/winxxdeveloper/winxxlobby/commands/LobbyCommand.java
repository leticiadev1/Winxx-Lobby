package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.LobbyManager;
import net.winxxdeveloper.winxxlobby.manager.PvpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LobbyCommand extends LobbyManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))return true;

        Player player=(Player)sender;
        FileConfiguration config = Terminal.getInstance().getConfig();

        if(command.getName().equalsIgnoreCase("lobby")){
            if(args.length == 0){
                if(config.getString("locations.spawn") == null){
                    player.sendMessage("§cO Spawn ainda não foi setado, contate um staff.");
                    if(player.hasPermission(config.getString("permissions.admin"))){
                        player.sendMessage("§7Utilize /lobby setspawn para setar o spawn.");
                    }
                    return true;
                }
                try{
                    player.teleport(getSavedLocation());
                    player.sendMessage(config.getString("teleports.tp-spawn")
                            .replace("&", "§"));
                }catch (Exception e){
                    e.printStackTrace();
                    player.sendMessage("§cNão foi possivel se teleportar para o spawn, reporte a um staff.");
                }
                return true;
            }
            if(args.length==1){
                if(player.hasPermission(config.getString("permissions.admin"))){
                    if(args[0].equalsIgnoreCase("setspawn")){
                        try{
                            saveLocation(player);
                            player.sendMessage("§eVocê setou a localização do spawn com sucesso.");
                        }catch (Exception e){
                            e.printStackTrace();
                            player.sendMessage("§cNão foi possivel setar a localização do spawn.");
                        }
                    }
                    if(args[0].equalsIgnoreCase("setpvp")){
                        if(config.getString("locations.spawn") == null){
                            player.sendMessage("§cVocê precisa setar o spawn do servidor primeiramente.");
                            return true;
                        }
                        try{
                            PvpManager.saveLocation(player);
                            player.sendMessage("§eVocê setou a localização da entrada do pvp com sucesso.");
                        }catch (Exception e){
                            e.printStackTrace();
                            player.sendMessage("§cNão foi possivel setar a localização da entrada do pvp.");
                        }
                    }
                }
            }
        }

        return false;
    }
}
