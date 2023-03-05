package net.winxxdeveloper.winxxlobby.listeners;

import net.winxxdeveloper.winxxlobby.manager.BuildingManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildingListener extends BuildingManager implements Listener {

    Player player;

    @EventHandler
    public void onPlayerBreakBlocks(BlockBreakEvent event){
        player=event.getPlayer();

        if(isBuildingList(player)){
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event){
        player=event.getPlayer();

        if(isBuildingList(player)){
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

}
