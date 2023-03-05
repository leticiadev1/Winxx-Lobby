package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.commands.*;
import net.winxxdeveloper.winxxlobby.listeners.*;
import org.bukkit.Bukkit;

public class PluginManager {

    public static void commandsManager(){
        Terminal.getInstance().getCommand("alerta").setExecutor(new AlertaCommand());
        Terminal.getInstance().getCommand("build").setExecutor(new BuildingCommand());
        Terminal.getInstance().getCommand("lobby").setExecutor(new LobbyCommand());
        Terminal.getInstance().getCommand("gm").setExecutor(new GamemodeCommand());
        Terminal.getInstance().getCommand("gmc").setExecutor(new GamemodeCommand());
        Terminal.getInstance().getCommand("gms").setExecutor(new GamemodeCommand());
        Terminal.getInstance().getCommand("fly").setExecutor(new FlyCommand());
        Terminal.getInstance().getCommand("clearchat").setExecutor(new ClearchatCommand());
    }

    public static void listenersManager(){
        Bukkit.getPluginManager().registerEvents(new BuildingListener(), Terminal.getInstance());
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), Terminal.getInstance());
        Bukkit.getPluginManager().registerEvents(new GamemodeListener(), Terminal.getInstance());
        Bukkit.getPluginManager().registerEvents(new PvpListener(), Terminal.getInstance());
        Bukkit.getPluginManager().registerEvents(new ServersListener(), Terminal.getInstance());
    }

}
