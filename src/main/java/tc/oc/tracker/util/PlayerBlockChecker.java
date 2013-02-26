package tc.oc.tracker.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class PlayerBlockChecker {
    public static final float PLAYER_HEIGHT = 1.8f;
    public static final float PLAYER_WIDTH = 0.6f;
    public static final float PLAYER_RADIUS = PLAYER_WIDTH / 2.0f;

    public static boolean isClimbing(Entity entity) {
        int blockId = entity.getWorld().getBlockTypeIdAt(entity.getLocation());
        return blockId == Material.LADDER.getId() || blockId == Material.VINE.getId();
    }

    public static boolean isSwimming(Entity entity, Material material) {
        Location location = entity.getLocation();
        World world = entity.getWorld();
        Material alternate = null;
        switch (material) {
            case WATER: alternate = Material.STATIONARY_WATER; break;
            case LAVA: alternate = Material.STATIONARY_LAVA; break;
            default: break;
        }

        int x1 = (int) Math.floor(location.getX() - PLAYER_RADIUS);
        int y1 = (int) Math.floor(location.getY());
        int z1 = (int) Math.floor(location.getZ() - PLAYER_RADIUS);

        int x2 = (int) Math.floor(location.getX() + PLAYER_RADIUS);
        int y2 = (int) Math.floor(location.getY() + PLAYER_HEIGHT);
        int z2 = (int) Math.floor(location.getZ() + PLAYER_RADIUS);

        for (int x = x1; x <= x2; ++x) {
            for (int y = y1; y <= y2; ++y) {
                for (int z = z1; z <= z2; ++z) {
                    Block block = world.getBlockAt(x,y,z);
                    if (block != null) {
                        Material type = block.getType();
                        if (type == material || type == alternate) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
