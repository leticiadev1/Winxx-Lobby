package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import org.bukkit.configuration.file.FileConfiguration;

public class AlertaManager {

    private static FileConfiguration discord = Terminal.discordConfig.getConfig();

    private static String alertaTitle=discord.getString("embeds.alerta-embed.title");
    private static String alertaDescription=discord.getString("embeds.alerta-embed.description")
            .replace("&", "§");
    private static String alertaFooter=discord.getString("embeds.alerta-embed.footer");
    private static String alertaImage=discord.getString("embeds.alerta-embed.image");

    public static String getAlertaFooter() {
        return alertaFooter;
    }

    public static String getAlertaImage() {
        return alertaImage;
    }

    public static String getAlertaDescription() {
        return alertaDescription;
    }

    public static String getAlertaTitle() {
        return alertaTitle;
    }

}
