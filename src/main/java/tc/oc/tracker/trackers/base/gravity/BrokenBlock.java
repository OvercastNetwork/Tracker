package tc.oc.tracker.trackers.base.gravity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import tc.oc.tracker.util.PlayerBlockChecker;

public class BrokenBlock {
    final public Block block;
    final public Player breaker;
    final public long time;

    public BrokenBlock(Block block, Player breaker, long time) {
        this.block = block;
        this.breaker = breaker;
        this.time = time;
    }

    public static BrokenBlock findBlockBrokenUnderPlayer(Player player, long time, HashMap<Location, BrokenBlock> blocks) {
        Location location = player.getLocation();

        int y = (int) Math.floor(location.getY() - 0.1);

        int x1 = (int) Math.floor(location.getX() - PlayerBlockChecker.PLAYER_RADIUS);
        int z1 = (int) Math.floor(location.getZ() - PlayerBlockChecker.PLAYER_RADIUS);

        int x2 = (int) Math.floor(location.getX() + PlayerBlockChecker.PLAYER_RADIUS);
        int z2 = (int) Math.floor(location.getZ() + PlayerBlockChecker.PLAYER_RADIUS);

        BrokenBlock lastBrokenBlock = null;

        for(int x = x1; x <= x2; ++x) {
            for(int z = z1; z <= z2; ++z) {
                Location bl = new Location(location.getWorld(), x, y, z);

                if(blocks.containsKey(bl)) {
                    BrokenBlock brokenBlock = blocks.get(bl);
                    if(lastBrokenBlock == null || brokenBlock.time > lastBrokenBlock.time) {
                        lastBrokenBlock = brokenBlock;
                    }
                }
            }
        }

        if(lastBrokenBlock != null && time - lastBrokenBlock.time <= SimpleGravityKillTracker.MAX_SPLEEF_TIME) {
            return lastBrokenBlock;
        } else {
            return null;
        }
    }
}
