package net.winxxdeveloper.winxxlobby.listeners;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.LobbyManager;
import net.winxxdeveloper.winxxlobby.manager.PvpManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PvpListener extends PvpManager implements Listener {

    Player player;
    FileConfiguration config = Terminal.getInstance().getConfig();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        player= event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getItemInHand();

        if(action.equals(Action.RIGHT_CLICK_AIR)) {
            if(itemInHand.hasItemMeta() && itemInHand != null){
                if(itemInHand.getItemMeta().getDisplayName().equals("§aEntrar no Modo PvP")){
                    if(config.getString("locations.pvp-entrada") == null){
                        player.sendMessage("§cA entrada do pvp ainda não foi setado, contate um staff.");
                        if(player.hasPermission(config.getString("permissions.admin"))){
                            player.sendMessage("§7Utilize /spawn setpvp para setar a entrada do pvp.");
                        }
                        return;
                    }
                    try {
                        player.teleport(getSavedLocation());
                        player.sendMessage(config.getString("teleports.tp-pvp")
                                .replace("&", "§"));
                        LobbyManager.limparJogador(player);
                        joinPvP(player);
                        addInPvpList(player);
                        player.teleport(getSavedLocation());
                    } catch (Exception e) {
                        e.printStackTrace();
                        player.sendMessage("§cNão foi possivel se teleportar para a entrada do pvp, reporte a um staff.");
                    }
                    return;
                }
                if(itemInHand.getItemMeta().getDisplayName().equals("§cSair do Modo PvP")){
                    removeInPvpList(player);
                    LobbyManager.limparJogador(player);
                    LobbyManager.setJoinItens(player);
                    player.teleport(LobbyManager.getSavedLocation());
                }
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        if(event.getInventory() != null){
            event.setCancelled(true);
            if(!inPvpList(player)) {
                return;
            }
            if(player.getInventory().contains(LobbyManager.pvp)){
                player.getInventory().remove(LobbyManager.pvp);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();
        if (killer instanceof Player) {
            if (PvpManager.inPvpList(player) && PvpManager.inPvpList(killer)) {
                for (Player var1 : Bukkit.getOnlinePlayers()) {
                    if (PvpManager.inPvpList(var1)) {
                        var1.sendMessage(config.getString("pvp-system.death-message")
                                .replace("&", "§")
                                .replace("{player}", player.getDisplayName())
                                .replace("{killer}", killer.getDisplayName()));
                    }
                }
            }
        }
        LobbyManager.limparJogador(player);
        LobbyManager.setJoinItens(player);
        try{
            player.teleport(getSavedLocation());
            player.sendMessage(config.getString("teleports.tp-spawn")
                    .replace("&", "§"));
        }catch (Exception e){
            e.printStackTrace();
            player.sendMessage("§cNão foi possivel se teleportar para o spawn, reporte a um staff.");
        }

    }

}
