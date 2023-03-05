package net.winxxdeveloper.winxxlobby.listeners;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.ServerSelectManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ServersListener extends ServerSelectManager implements Listener {

    FileConfiguration config = Terminal.serversConfig.getConfig();
    Player player;

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        player=event.getPlayer();
        Action action=event.getAction();
        ItemStack itemInHand=player.getItemInHand();

        if(action.equals(Action.RIGHT_CLICK_AIR)){
            if(itemInHand.hasItemMeta() && itemInHand != null){
                if(itemInHand.getItemMeta().getDisplayName().equals("§aSeletor de Servidores")){
                    createNewServer();
                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        player=(Player)event.getWhoClicked();
        Inventory inventory=event.getClickedInventory();
        ItemStack currentItem=event.getCurrentItem();

        if(inventory.getTitle().equals(config.getString("server-selector.gui-menu.title")
                .replace("&", "§"))){
            if(currentItem.hasItemMeta() && currentItem != null){
                event.setCancelled(true);
                sendToServer(player, currentItem);
            }
        }
    }

}
