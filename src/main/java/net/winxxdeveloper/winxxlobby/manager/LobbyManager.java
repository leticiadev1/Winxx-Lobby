package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LobbyManager {

    private static FileConfiguration config = Terminal.getInstance().getConfig();

    private static List<String> hideList = new ArrayList<>();

    private static Date date = new Date();
    private static Date hour = new Date();
    private static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");

    public static String getDate(){
        return formatDate.format(date);
    }

    public static String getHour(){
        return formatHour.format(hour);
    }

    public static boolean isHideList(Player player) {
        if(hideList.contains(player.getName())){
            return true;
        }
        return false;
    }

    public static void setPlayerOnHideList(Player player) {
        if(!isHideList(player)){
            hideList.add(player.getName());
            player.getInventory().remove(hidePlayers);
            player.getInventory().setItem(7 - 1, showPlayers);
            for(Player p : Bukkit.getOnlinePlayers()){
                player.showPlayer(p);
            }
        }
    }

    public static void removePlayerOnHideList(Player player) {
        if(isHideList(player)){
            hideList.remove(player.getName());
            player.getInventory().remove(showPlayers);
            player.getInventory().setItem(7 - 1, hidePlayers);
            for(Player p : Bukkit.getOnlinePlayers()){
                player.hidePlayer(p);
            }
        }
    }

    private static ItemStack serverSelector = new ItemBuilder(Material.COMPASS)
            .setName("§aSeletor de Servidores")
            .setLore("", "§7Clique com o botão direito", "§7para abrir o seletor de servidores.").build();

    private static ItemStack hidePlayers = new ItemBuilder(Material.INK_SACK)
            .setName("§aEsconder Jogadores")
            .setLore("", "§7Clique com o botão direito", "§7para esconder os jogadores")
            .setDyeColor(DyeColor.LIME).build();

    private static ItemStack showPlayers = new ItemBuilder(Material.INK_SACK)
            .setName("§7Aparecer Jogadores")
            .setLore("", "§7Clique com o botão direito", "§7para aparecer os jogadores")
            .setDyeColor(DyeColor.GRAY).build();
    private static ItemStack magicBow = new ItemBuilder(Material.BOW)
            .setName("§aArco mágico")
            .setLore("","§7Quando você acertar uma flecha em algum local", "§7você irá se teleportar para o local.")
            .addEnchant(Enchantment.ARROW_INFINITE, 1)
            .setDurability((short) 0).build();

    public static ItemStack pvp = new ItemBuilder(Material.GOLD_SWORD)
            .setName("§aEntrar no Modo PvP")
            .setLore("", "§7Clique com o botão direito", "§7para entrar no modo pvp.").build();

    public static void saveLocation(Player player){

        String world = player.getWorld().getName();
        double locX = Double.valueOf(player.getLocation().getX());
        double locY = Double.valueOf(player.getLocation().getY());
        double locZ = Double.valueOf(player.getLocation().getZ());
        String location = world + ", " + locX + ", " + locY + ", " + locZ;
        config.set("locations.spawn", location);

        Terminal.getInstance().saveConfig();

    }

    public static Location getSavedLocation(){

        String loc = config.getString("locations.spawn");
        String args[] = loc.split(", ");
        World world = Bukkit.getWorld(args[0]);
        double X = Double.valueOf(args[1]);
        double Y = Double.valueOf(args[2]);
        double Z = Double.valueOf(args[3]);
        Location location = new Location(world, X, Y, Z);

        return location;
    }

    public static void limparJogador(Player player){
        player.setTotalExperience(0);
        player.setExp(0);
        player.getEquipment().setBoots(null);
        player.getEquipment().setLeggings(null);
        player.getEquipment().setChestplate(null);
        player.getEquipment().setHelmet(null);
        player.getInventory().clear();
    }

    public static void setJoinItens(Player player){
        player.getInventory().setHeldItemSlot(5 - 1);
        player.getInventory().setItem(1 - 1, magicBow);
        player.getInventory().setItem(3 - 1, pvp);
        player.getInventory().setItem(5 - 1, serverSelector);
        player.getInventory().setItem(7 - 1, hidePlayers);
        player.getInventory().setItem(28 - 1, new ItemBuilder(Material.ARROW)
                .setName("§aFlecha do Arco mágico")
                .setLore("", "§7Use esta flecha mágica", "§7com o seu arco mágico.").build());
    }
}
