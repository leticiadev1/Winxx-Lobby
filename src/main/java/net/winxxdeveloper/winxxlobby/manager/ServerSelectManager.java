package net.winxxdeveloper.winxxlobby.manager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.utils.ActionBar;
import net.winxxdeveloper.winxxlobby.utils.BungeeChannelApi;
import net.winxxdeveloper.winxxlobby.utils.ItemBuilder;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServerSelectManager {

    private static FileConfiguration config = Terminal.serversConfig.getConfig();

    public static Inventory inventory = Bukkit.createInventory(null, 3*9, config.getString("server-selector.gui-menu.title").replace("&", "§"));

    private static ItemStack getSkull(String url) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public void createNewServer(){

        for(String var1 : config.getConfigurationSection("server-selector.gui-menu.servers").getKeys(false)){

            List<String> var10 = config.getStringList("server-selector.gui-menu.servers."+var1+".lore");
            List<String> var11 = var10.stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());

            ItemStack var2 = new ItemBuilder(getSkull(config.getString("server-selector.gui-menu.servers."+var1+".texture-head")))
                    .setName(config.getString("server-selector.gui-menu.servers."+var1+".name").replace("&", "§"))
                    .setLore(var11).build();

            inventory.setItem(config.getInt("server-selector.gui-menu.servers."+var1+".slot"), var2);

        }
    }

    public static void sendToServer(Player player, ItemStack item){
        for(String var1 : config.getConfigurationSection("server-selector.gui-menu.servers").getKeys(false)){
            if(item.getItemMeta().getDisplayName().equals(config.getString("server-selector.gui-menu.servers."+var1+".name")
                    .replace("&", "§"))){
                player.closeInventory();
                BungeeChannelApi.of(Terminal.getInstance()).connect(player, config.getString("server-selector.gui-menu.servers."+var1+".server"));
                ActionBar.sendActionbar(player, config.getString("server-selector.gui-menu.connect-message")
                        .replace("&", "§")
                        .replace("{server_name}", config.getString("server-selector.gui-menu.servers."+var1+".name")
                                .replace("&", "§")));
            }
        }
    }

}
