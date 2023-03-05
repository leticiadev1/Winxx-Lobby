package net.winxxdeveloper.winxxlobby.manager;

import org.bukkit.entity.Player;

public class FlyManager {

    public static boolean isFlying(Player player){
        if(player.getAllowFlight()){
            return true;
        }
        return false;
    }

}
