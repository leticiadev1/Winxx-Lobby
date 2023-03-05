package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.AlertaManager;
import net.winxxdeveloper.winxxlobby.manager.DiscordManager;
import net.winxxdeveloper.winxxlobby.manager.LobbyManager;
import net.winxxdeveloper.winxxlobby.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;

public class AlertaCommand extends AlertaManager implements CommandExecutor {

    FileConfiguration config = Terminal.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))return false;

        Player player = (Player)sender;

        if(command.getName().equalsIgnoreCase("alerta")){
            if(!player.hasPermission(config.getString("permissions.admin"))){
                player.sendMessage("§cVocê não tem permissão para executar este comando!");
                return true;
            }
            if(args.length == 0){
                player.sendMessage("§aUtilize o comando neste modelo: §7/alerta <mensagem>");
                return true;
            }
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < args.length; i++){
                builder.append(args[i]);
                builder.append(" ");
            }

            String finalMessage = builder.toString();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("  §b§lALERTA: §r"+finalMessage.replace("§", "&"));
            Bukkit.broadcastMessage("");

            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(getAlertaTitle())
                    .setDescription(getAlertaDescription()
                            .replace("{staffer}", player.getDisplayName())
                            .replace("{alerta}", "\\n\\n"+finalMessage)
                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                    "\\nHorário: "+LobbyManager.getHour()))
                    .setColor(Color.CYAN)
                    .setFooter(getAlertaFooter(), getAlertaImage()));
            try{
                webhook.execute();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return false;
    }
}
