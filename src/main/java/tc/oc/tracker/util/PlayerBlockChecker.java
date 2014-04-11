package tc.oc.tracker.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class PlayerBlockChecker {
    public static boolean isClimbing(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.LADDER || material == Material.VINE;
    }

    public static boolean isSwimming(Location location, Material liquidType) {
        Material material = location.getBlock().getType();
        switch(liquidType) {
            case WATER:
            case STATIONARY_WATER:
                return material == Material.WATER || material == Material.STATIONARY_WATER;

            case LAVA:
            case STATIONARY_LAVA:
                return material == Material.LAVA || material == Material.STATIONARY_LAVA;

            default:
                return false;
        }
    }
}
