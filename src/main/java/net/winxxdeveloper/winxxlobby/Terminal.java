package net.winxxdeveloper.winxxlobby;

import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.winxxdeveloper.winxxlobby.animations.runnables.AnimationRunnable;
import net.winxxdeveloper.winxxlobby.manager.BuildingManager;
import net.winxxdeveloper.winxxlobby.manager.PluginManager;
import net.winxxdeveloper.winxxlobby.manager.PvpManager;
import net.winxxdeveloper.winxxlobby.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Terminal extends JavaPlugin {

    private static Terminal instance;
    public static Config discordConfig;
    public static Config serversConfig;

    public static Terminal getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        long timeAtStart = System.currentTimeMillis();
        instance=this;
        configHandler();
        PluginManager.commandsManager();
        PluginManager.listenersManager();

        long timeAtEnd = System.currentTimeMillis();
        long ms = timeAtEnd - timeAtStart;
        getLogger().info("[Winxx Lobby] Plugin inicializado em: "+ms+"ms.");
        if(!discordConfig.getConfig().getString("discord-integration.webhook").startsWith("https://discord.com/api/webhooks/")){
            getLogger().info("");
            getLogger().info("[Winxx Lobby] Foi impossivel realizar a integração com o discord!");
            getLogger().info("");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        for(Player var1 : Bukkit.getOnlinePlayers()){
            if(BuildingManager.isBuildingList(var1)){
                BuildingManager.removeBuildPlayer(var1);
            }
            if(PvpManager.inPvpList(var1)){
                PvpManager.removeInPvpList(var1);
            }
        }

        new AnimationRunnable(this).runTaskTimerAsynchronously(this, 0L, 1L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        long timeAtStart = System.currentTimeMillis();
        long timeAtEnd = System.currentTimeMillis();
        long ms = timeAtEnd - timeAtStart;
        getLogger().info("[Winxx Lobby] Plugin desligado em: "+ms+"ms.");
        saveConfig();
    }

    public static void configHandler(){
        discordConfig = new Config(Terminal.getInstance(), "discord.yml");
        serversConfig = new Config(Terminal.getInstance(), "servidores.yml");

        Terminal.getInstance().saveDefaultConfig();
        discordConfig.saveDefaultConfig();
        serversConfig.saveDefaultConfig();

        createFile(Terminal.getInstance(), Terminal.getInstance().getDataFolder().getAbsolutePath(), false);
    }

    public static void createFile(Terminal main, String fileName, boolean isFile) {
        try {
            File file = new File(main.getDataFolder() + File.separator + fileName);
            if (isFile) file.createNewFile();
            else if (!file.exists()) file.mkdirs();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerConnection getConnection(Player player){
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

}
