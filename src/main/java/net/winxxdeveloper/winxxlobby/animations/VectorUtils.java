package net.winxxdeveloper.winxxlobby.animations;

import org.bukkit.util.Vector;

public class VectorUtils {
    public static Vector rotateY(Vector vector, double angle){
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = vector.getX() * cos+vector.getZ() * sin;
        double z = vector.getX() * -sin+vector.getZ() * cos;

        return vector.setX(x).setZ(z);
    }
}
