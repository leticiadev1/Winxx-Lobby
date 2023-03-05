package net.winxxdeveloper.winxxlobby.listeners;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.*;
import net.winxxdeveloper.winxxlobby.utils.ActionBar;
import net.winxxdeveloper.winxxlobby.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.awt.*;
import java.io.IOException;

public class LobbyListener extends LobbyManager implements Listener {

    Player player;

    private static FileConfiguration config = Terminal.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        player=event.getPlayer();
        if(event.getJoinMessage() != null){
            event.setJoinMessage(null);
        }

        DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(ReceptionManager.getReceptionTitle())
                .setDescription(ReceptionManager.getReceptionDescription()
                        .replace("{jogador}", player.getDisplayName())
                        .replace("{recepcao}", "entrou")
                        .replace("{date}", "\\n\\nData: "+getDate()+
                                "\\nHorário: "+getHour()))
                .setColor(Color.GREEN)
                .setFooter(ReceptionManager.getReceptionFooter(), ReceptionManager.getReceptionImage()));
        try{
            webhook.execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        limparJogador(player);
        setJoinItens(player);

        if(!config.getBoolean("lobby-system.one-hearth")){
            player.setHealth(20);
            player.setMaxHealth(20);
            return;
        }
        player.setHealth(1);
        player.setMaxHealth(2);
        player.setFoodLevel(20);

        if(BuildingManager.isBuildingList(player)){
            BuildingManager.removeBuildPlayer(player);
        }
        if(isHideList(player)){
            removePlayerOnHideList(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        player=event.getPlayer();

        DiscordWebhook webhook = new DiscordWebhook(DiscordManager.getWebhook_url());
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setTitle(ReceptionManager.getReceptionTitle())
                .setDescription(ReceptionManager.getReceptionDescription()
                        .replace("{jogador}", player.getDisplayName())
                        .replace("{recepcao}", "deslogou")
                        .replace("{date}", "\\n\\nData: "+getDate()+
                                "\\nHorário: "+getHour()))
                .setColor(Color.RED)
                .setFooter(ReceptionManager.getReceptionFooter(), ReceptionManager.getReceptionImage()));
        try{
            webhook.execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(event.getQuitMessage() != null){
            event.setQuitMessage(null);
        }

        if(PvpManager.inPvpList(player)){
            PvpManager.removeInPvpList(player);
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){

        player=(Player)event.getWhoClicked();

        if(event.getInventory() != null){
            if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem() != null){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aSeletor de Servidores")){
                    event.setCancelled(true);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aEsconder Jogadores")){
                    event.setCancelled(true);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§7Aparecer Jogadores")){
                    event.setCancelled(true);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aFlecha do Arco mágico")){
                    event.setCancelled(true);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aArco mágico")){
                    event.setCancelled(true);
                }
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aEntrar no Modo PvP")){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        player=event.getPlayer();
        Action act = event.getAction();

        if(act.equals(Action.RIGHT_CLICK_BLOCK) || act.equals(Action.RIGHT_CLICK_AIR)){
            if(player.getItemInHand().hasItemMeta() && player.getItemInHand() != null){
                if(player.getItemInHand().getItemMeta().getDisplayName().equals("§aEsconder Jogadores")){
                    player.sendMessage("§cVocê escondeu todos os jogadores!");
                    if(!isHideList(player)){
                        setPlayerOnHideList(player);
                    }
                    return;
                }
                if(player.getItemInHand().getItemMeta().getDisplayName().equals("§7Aparecer Jogadores")){
                    player.sendMessage("§aTodos os jogadores apareceram de volta!");
                    if(isHideList(player)){
                        removePlayerOnHideList(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if(!BuildingManager.isBuildingList(event.getPlayer())){
            event.setCancelled(true);
            return;
        }
        event.setCancelled(false);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            if(PvpManager.inPvpList(player)){
                event.setCancelled(false);
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){

        player=(Player) event.getEntity().getShooter();
        Location shootedLocation = event.getEntity().getLocation();

        if(event.getEntity().getShooter() instanceof Player){
            if(!player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals("§aArco mágico")){
                return;
            }
            if(player.getItemInHand().hasItemMeta() && player.getItemInHand() != null){
                player.getInventory().getItemInHand().setDurability((short) 0);
                player.teleport(shootedLocation);
                event.getEntity().remove();
                ActionBar.sendActionbar(player, "§eVocê como um arqueiro profissional, se teleportou para sua flecha.");
                player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }

    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if(BuildingManager.isBuildingList(player)){
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

}
