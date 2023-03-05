package net.winxxdeveloper.winxxlobby.manager;


import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuildingManager {

    private static List<String> buildingList = new ArrayList<>();
    private static FileConfiguration discord = Terminal.discordConfig.getConfig();

    public static boolean isBuildingList(Player player){
        if(buildingList.contains(player.getName())){
            return true;
        }
        return false;
    }

    public static void addBuildPlayer(Player player){
        if(!isBuildingList(player)){
            buildingList.add(player.getName());
            setCapaConstrutor(player);
        }
    }

    public static void removeBuildPlayer(Player player){
        if(isBuildingList(player)){
            buildingList.remove(player.getName());
            removeCapaConstrutor(player);
        }
    }

    public static void setCapaConstrutor(Player player){
        if(isBuildingList(player)){
            ItemStack capacete = new ItemBuilder(Material.GOLD_HELMET)
                    .setName("§eChapéu Construtor")
                    .setLore("", "§7Este chapéu significa que você", "§7está usando o modo construtor!")
                    .setDurability((short) 0).build();
            player.getEquipment().setHelmet(capacete);
        }
    }

    public static void removeCapaConstrutor(Player player){
        if(!isBuildingList(player)){
            if(player.getEquipment().getHelmet().hasItemMeta()) {
                if(player.getEquipment().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase("§eChapéu Construtor")) {
                    player.getEquipment().setHelmet(null);
                }
            }
        }
    }

    private static String buildingTitle=discord.getString("embeds.building-embed.title");
    private static String buildingDescription=discord.getString("embeds.building-embed.description")
            .replace("&", "§");
    private static String buildingFooter=discord.getString("embeds.building-embed.footer");
    private static String buildingImage=discord.getString("embeds.building-embed.image");

    public static String getBuildingTitle() {
        return buildingTitle;
    }

    public static String getBuildingDescription() {
        return buildingDescription;
    }

    public static String getBuildingFooter() {
        return buildingFooter;
    }

    public static String getBuildingImage() {
        return buildingImage;
    }
}
