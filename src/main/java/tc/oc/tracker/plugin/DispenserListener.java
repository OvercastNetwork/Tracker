package tc.oc.tracker.plugin;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tc.oc.tracker.trackers.DispenserTracker;

public class DispenserListener implements Listener {
    private final DispenserTracker tracker;

    public DispenserListener(DispenserTracker tracker) {
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!this.tracker.isEnabled(event.getBlock().getWorld())) return;

        if (event.getBlock().getType() == Material.DISPENSER) {
            this.tracker.setPlacer(event.getBlock(), event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(!this.tracker.isEnabled(event.getBlock().getWorld())) return;

        this.tracker.setPlacer(event.getBlock(), null);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(EntityExplodeEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        // Remove all blocks that are destroyed from explosion
        for(Iterator<Block> it = event.blockList().iterator(); it.hasNext(); ) {
            Block block = it.next();
            this.tracker.setPlacer(block, null);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onShoot(BlockDispenseEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        Block block = event.getBlock();
        Player placer = this.tracker.getPlacer(block);
        if (placer != null) {
            this.tracker.setOwner(event.getEntity(), block.getState());
        }
    }
}
