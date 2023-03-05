package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.utils.ActionBar;
import net.winxxdeveloper.winxxlobby.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.awt.*;
import java.io.IOException;

public class GamemodeManager {

    private static FileConfiguration discord = Terminal.discordConfig.getConfig();
    private static String gamemodeInventoryName="§7Menu de Gamemodes";
    public static Inventory gamemodeInventory = Bukkit.createInventory(null, 3*9, gamemodeInventoryName);
    public static String getGmInvName(){
        return gamemodeInventoryName;
    }

    public static boolean isCreativeMode(Player player){
        if(player.getGameMode().equals(GameMode.CREATIVE)){
            return true;
        }
        return false;
    }

    public static void setGamemodeCriativo(Player player){
        if(!isCreativeMode(player)){
            player.setGameMode(GameMode.CREATIVE);
            ActionBar.sendActionbar(player, "§aVocê entrou no modo criativo.");

            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(getGamemodeTitle())
                    .setDescription(getGamemodeDescription()
                            .replace("{staffer}", player.getDisplayName())
                            .replace("{gamemode}", "CRIATIVO")
                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                    "\\nHorário: "+LobbyManager.getHour()))
                    .setColor(Color.GREEN)
                    .setFooter(getGamemodeFooter(), getGamemodeImage()));
            try{
                webhook.execute();
            }catch (IOException e){
                e.printStackTrace();
            }

            return;
        }
        ActionBar.sendActionbar(player,"§cVocê já está no modo criativo.");
    }

    public static void setGamemodeSurvival(Player player){
        if(player.getGameMode() != GameMode.SURVIVAL){
            player.setGameMode(GameMode.SURVIVAL);
            ActionBar.sendActionbar(player, "§aVocê entrou no modo survival.");

            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(getGamemodeTitle())
                    .setDescription(getGamemodeDescription()
                            .replace("{staffer}", player.getDisplayName())
                            .replace("{gamemode}", "SURVIVAL")
                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                    "\\nHorário: "+LobbyManager.getHour()))
                    .setColor(Color.RED)
                    .setFooter(getGamemodeFooter(), getGamemodeImage()));
            try{
                webhook.execute();
            }catch (IOException e){
                e.printStackTrace();
            }

            return;
        }
        ActionBar.sendActionbar(player,"§cVocê já está no modo survival.");
    }

    public static void setGamemodeEspectador(Player player){
        if(!player.getGameMode().equals(GameMode.SPECTATOR)){
            player.setGameMode(GameMode.SPECTATOR);
            ActionBar.sendActionbar(player, "§aVocê entrou no modo espectador.");

            DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(getGamemodeTitle())
                    .setDescription(getGamemodeDescription()
                            .replace("{staffer}", player.getDisplayName())
                            .replace("{gamemode}", "ESPECTADOR")
                            .replace("{date}", "\\n\\nData: "+ LobbyManager.getDate()+
                                            "\\nHorário: "+LobbyManager.getHour()))
                    .setColor(Color.DARK_GRAY)
                    .setFooter(getGamemodeFooter(), getGamemodeImage()));
            try{
                webhook.execute();
            }catch (IOException e){
                e.printStackTrace();
            }

            return;
        }
        ActionBar.sendActionbar(player,"§cVocê já está no modo espectador.");
    }

    private static String gamemodeTitle=discord.getString("embeds.gamemode-embed.title");
    private static String gamemodeDescription=discord.getString("embeds.gamemode-embed.description")
            .replace("&", "§");
    private static String gamemodeFooter=discord.getString("embeds.gamemode-embed.footer");
    private static String gamemodeImage=discord.getString("embeds.gamemode-embed.image");

    public static String getGamemodeTitle() {
        return gamemodeTitle;
    }

    public static String getGamemodeDescription() {
        return gamemodeDescription;
    }

    public static String getGamemodeFooter() {
        return gamemodeFooter;
    }

    public static String getGamemodeImage() {
        return gamemodeImage;
    }
}
