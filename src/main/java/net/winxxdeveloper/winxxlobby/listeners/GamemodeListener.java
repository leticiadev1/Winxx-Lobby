package net.winxxdeveloper.winxxlobby.listeners;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.GamemodeManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GamemodeListener extends GamemodeManager implements Listener {

    Player player;
    FileConfiguration config = Terminal.getInstance().getConfig();

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        player=(Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack currentItem = event.getCurrentItem();

        if(inventory.getTitle().equals(getGmInvName())){
            event.setCancelled(true);
            if(player.hasPermission(config.getString("permissions.admin"))){
                if(currentItem.hasItemMeta() && currentItem != null){
                    if(currentItem.getItemMeta().getDisplayName().equals("§aGAMEMODE CRIATIVO")){
                        setGamemodeCriativo(player);
                        player.closeInventory();
                    }
                    if(currentItem.getItemMeta().getDisplayName().equals("§aGAMEMODE SURVIVAL")){
                        setGamemodeSurvival(player);
                        player.closeInventory();
                    }
                    if(currentItem.getItemMeta().getDisplayName().equals("§aGAMEMODE ESPECTADOR")){
                        setGamemodeEspectador(player);
                        player.closeInventory();
                    }
                }
            }
        }
    }

}
