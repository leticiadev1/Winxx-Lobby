package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import org.bukkit.configuration.file.FileConfiguration;

public class ReceptionManager {
    private static FileConfiguration discord = Terminal.discordConfig.getConfig();

    private static String receptionTitle=discord.getString("embeds.recepcao-embed.title");
    private static String receptionDescription=discord.getString("embeds.recepcao-embed.description")
            .replace("&", "§");
    private static String receptionFooter=discord.getString("embeds.recepcao-embed.footer");
    private static String receptionImage=discord.getString("embeds.recepcao-embed.image");

    public static String getReceptionTitle() {
        return receptionTitle;
    }

    public static String getReceptionDescription() {
        return receptionDescription;
    }

    public static String getReceptionFooter() {
        return receptionFooter;
    }

    public static String getReceptionImage() {
        return receptionImage;
    }
}
