package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import org.bukkit.configuration.file.FileConfiguration;

public class DiscordManager {

    private static FileConfiguration discord = Terminal.discordConfig.getConfig();
    private static String webhook_url=discord.getString("discord-integration.webhook");

    public static String getWebhook_url() {
        return webhook_url;
    }
}
