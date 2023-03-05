package net.winxxdeveloper.winxxlobby.commands;

import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.manager.GamemodeManager;
import net.winxxdeveloper.winxxlobby.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GamemodeCommand extends GamemodeManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player=(Player)sender;
        FileConfiguration config = Terminal.getInstance().getConfig();

        if(command.getName().equalsIgnoreCase("gm")){

            if(player.hasPermission(config.getString("permissions.admin"))){
                if(args.length == 0){

                    gamemodeInventory.setItem(11, new ItemBuilder(Material.WOOL)
                            .setName("§aGAMEMODE CRIATIVO")
                            .setLore("", "§7Clique com botão direito", "§7para entrar no modo criativo.")
                            .setWoolColor(DyeColor.PURPLE).build());
                    gamemodeInventory.setItem(13, new ItemBuilder(Material.WOOL)
                            .setName("§aGAMEMODE SURVIVAL")
                            .setLore("", "§7Clique com botão direito", "§7para entrar no modo criativo.")
                            .setWoolColor(DyeColor.BLUE).build());
                    gamemodeInventory.setItem(15, new ItemBuilder(Material.WOOL)
                            .setName("§aGAMEMODE ESPECTADOR")
                            .setLore("", "§7Clique com botão direito", "§7para entrar no modo criativo.")
                            .setWoolColor(DyeColor.SILVER).build());


                    player.openInventory(gamemodeInventory);

                    return true;
                }
                if(args.length==1){
                    if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") ||
                    args[0].equalsIgnoreCase("0")){
                        setGamemodeSurvival(player);
                    }
                    if(args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") ||
                    args[0].equalsIgnoreCase("criativo") || args[0].equalsIgnoreCase("1")){
                        setGamemodeCriativo(player);
                    }
                }
            }

        }

        if(command.getName().equalsIgnoreCase("gmc")){
            if(player.hasPermission(config.getString("permissions.admin"))){
                setGamemodeCriativo(player);
            }
        }
        if(command.getName().equalsIgnoreCase("gms")){
            if(player.hasPermission(config.getString("permissions.admin"))){
                setGamemodeSurvival(player);
            }
        }

        return false;
    }
}
