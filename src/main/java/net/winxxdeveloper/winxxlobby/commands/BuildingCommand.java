package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.BuildingManager;
import net.winxxdeveloper.winxxlobby.manager.DiscordManager;
import net.winxxdeveloper.winxxlobby.manager.LobbyManager;
import net.winxxdeveloper.winxxlobby.utils.DiscordWebhook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingCommand extends BuildingManager implements CommandExecutor {

    public static FileConfiguration config = Terminal.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))return true;

        Player player=(Player)sender;

        if(command.getName().equalsIgnoreCase("build")){
            if(args.length==0) {

                if(player.hasPermission(config.getString("permissions.admin"))){

                    List<String> list = config.getStringList("build-system.syntax");
                    List<String> replacedList = list.stream().map(s -> s
                            .replace("&", "§")).collect(Collectors.toList());

                    for(String s : replacedList){
                        player.sendMessage(s);
                    }

                }

                return true;
            }
            if(args.length==1){
                if(player.hasPermission(config.getString("permissions.admin"))){
                    if(args[0].equalsIgnoreCase("on")){
                        if(!isBuildingList(player)){
                            player.sendMessage(config.getString("build-system.modes.ativado")
                                    .replace("&", "§"));
                            addBuildPlayer(player);
                            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle(getBuildingTitle())
                                    .setDescription(getBuildingDescription()
                                            .replace("{staffer}", player.getDisplayName())
                                            .replace("{building}", "ativou")
                                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                                    "\\nHorário: "+LobbyManager.getHour()))
                                    .setColor(Color.GREEN)
                                    .setFooter(getBuildingFooter(), getBuildingImage()));
                            try{
                                webhook.execute();
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                            return true;
                        }
                        player.sendMessage("§cO modo construtor já está ativado.");
                    }
                    if(args[0].equalsIgnoreCase("off")){
                        if(isBuildingList(player)){
                            player.sendMessage(config.getString("build-system.modes.desativado")
                                    .replace("&", "§"));
                            removeBuildPlayer(player);
                            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle(getBuildingTitle())
                                    .setDescription(getBuildingDescription()
                                            .replace("{staffer}", player.getDisplayName())
                                            .replace("{building}", "desativou")
                                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                                    "\\nHorário: "+LobbyManager.getHour()))
                                    .setColor(Color.RED)
                                    .setFooter(getBuildingFooter(), getBuildingImage()));
                            try{
                                webhook.execute();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            return true;
                        }
                        player.sendMessage("§cO modo construtor já está desativado.");
                    }
                }
            }
        }

        return false;
    }
}
