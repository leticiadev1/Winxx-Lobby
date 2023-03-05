package net.winxxdeveloper.winxxlobby.runnables;

import net.winxxdeveloper.winxxlobby.Terminal;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AlwaysDayRunnable extends BukkitRunnable {

    Terminal terminal;

    public AlwaysDayRunnable(Terminal terminal){
        this.terminal = terminal;
    }

    @Override
    public void run() {
        Bukkit.getWorld(Terminal.getInstance().getConfig().getString("world-name")).setTime(0L);
    }
}
