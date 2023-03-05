package net.winxxdeveloper.winxxlobby.manager;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PvpManager {

    private static FileConfiguration config = Terminal.getInstance().getConfig();
    private static List<UUID> inPvp = new ArrayList<>();

    public static void saveLocation(Player player){

        String world = player.getWorld().getName();
        double locX = Double.valueOf(player.getLocation().getX());
        double locY = Double.valueOf(player.getLocation().getY());
        double locZ = Double.valueOf(player.getLocation().getZ());
        String location = world + ", " + locX + ", " + locY + ", " + locZ;
        config.set("locations.pvp-entrada", location);

        Terminal.getInstance().saveConfig();

    }

    public static Location getSavedLocation(){

        String loc = config.getString("locations.pvp-entrada");
        String args[] = loc.split(", ");
        World world = Bukkit.getWorld(args[0]);
        double X = Double.valueOf(args[1]);
        double Y = Double.valueOf(args[2]);
        double Z = Double.valueOf(args[3]);
        Location location = new Location(world, X, Y, Z);

        return location;
    }

    public static void joinPvP(Player player){

        player.sendMessage("§eVocê entrou no Modo PvP §7- §cTENHA CUIDADO!");
        player.setMaxHealth(20f);
        player.setHealth(20f);

        player.getEquipment().setHelmet(new ItemBuilder(Material.IRON_HELMET)
                .setName("§fCapacete de Zeus")
                .setLore("", "§7Este capacete foi usado", "§7por um antigo §bDeus §7grego.").build());
        player.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE)
                .setName("§fPeitoral de Zeus")
                .setLore("", "§7Este peitoral foi usado", "§7por um antigo §bDeus §7grego.").build());
        player.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS)
                .setName("§fCapacete de Zeus")
                .setLore("", "§7Esta calça foi usado", "§7por um antigo §bDeus §7grego.").build());
        player.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS)
                .setName("§fBotas de Zeus")
                .setLore("", "§7Esta bota foi usado", "§7por um antigo §bDeus §7grego.").build());

        player.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD)
                .setName("§fEspada de Marmore")
                .setLore("", "§7Apenas Deuses poderosos possúem", "§7a grande e desejada espada de marmore.").build());
        player.getInventory().setItem(1, new ItemBuilder(Material.GOLDEN_APPLE, 16)
                .setName("§6Maça Divina")
                .setLore("", "§7Coma esta maça para receber poderes.").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.BED)
                .setName("§cSair do Modo PvP")
                .setLore("", "§7Clique com botão direito", "§7para sair do modo pvp.").build());
    }

    public static boolean inPvpList(Player player){
        if(inPvp.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static void addInPvpList(Player player){
        if(!inPvpList(player)){
            inPvp.add(player.getUniqueId());
        }
    }

    public static void removeInPvpList(Player player){
        if(inPvpList(player)){
            inPvp.remove(player.getUniqueId());
        }
    }

}
