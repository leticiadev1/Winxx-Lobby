package net.winxxdeveloper.winxxlobby.animations.runnables;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.winxxdeveloper.winxxlobby.Terminal;
import net.winxxdeveloper.winxxlobby.animations.VectorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AnimationRunnable extends BukkitRunnable {

    protected Terminal terminal;

    public AnimationRunnable(Terminal terminal){
        this.terminal = terminal;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().stream().filter(Player::getAllowFlight).forEach(players -> {
            Location location = players.getLocation();
            for(double theta =0; theta < Math.PI*2;theta+=Math.PI/48){
                double offset = (Math.pow(Math.E, Math.cos(theta)) - 2 * Math.cos(theta*4) - Math.pow(Math.sin(theta / 12), 5)) / 2;

                double x = Math.sin(theta)*offset;
                double y = Math.cos(theta)*offset;
                Vector vector = VectorUtils.rotateY(new Vector(x, y, -0.45), -Math.toRadians(location.getYaw()));

                float locX = (float) (location.getX()+vector.getX());
                float locY = (float) (location.getY()+vector.getY());
                float locZ = (float) (location.getZ()+vector.getZ());

                Color color = Color.YELLOW;
                float red = color.getRed();
                float green = color.getGreen();
                float blue = color.getBlue();

                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, locX, locY, locZ, red, green, blue, 1, 0);
                terminal.getConnection(players).sendPacket(packet);
            }
        });
    }

}
