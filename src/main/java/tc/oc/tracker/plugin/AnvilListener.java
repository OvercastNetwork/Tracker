package tc.oc.tracker.plugin;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tc.oc.tracker.event.BlockFallEvent;
import tc.oc.tracker.trackers.AnvilTracker;

public class AnvilListener implements Listener {
    private final AnvilTracker tracker;

    public AnvilListener(AnvilTracker tracker) {
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!this.tracker.isEnabled(event.getBlock().getWorld())) return;

        if(event.getBlock().getType() == Material.ANVIL) {
            this.tracker.setPlacer(event.getBlock(), Bukkit.getOfflinePlayer(event.getPlayer().getName()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(!this.tracker.isEnabled(event.getBlock().getWorld())) return;

        this.tracker.clearPlacer(event.getBlock());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(EntityExplodeEvent event) {
        if(!this.tracker.isEnabled(event.getLocation().getWorld())) return;

        // Remove all blocks that are destroyed from explosion
        for (Block block : event.blockList()) {
            this.tracker.clearPlacer(block);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnvilFall(BlockFallEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        if(event.getBlock().getType() == Material.ANVIL) {
            OfflinePlayer placer = tracker.getPlacer(event.getBlock());

            if(placer != null) {
                tracker.setOwner(event.getEntity(), tracker.getPlacer(event.getBlock()));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnvilLand(EntityChangeBlockEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        if(event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.ANVIL) {
            OfflinePlayer owner = tracker.getOwner((FallingBlock) event.getEntity());
            if(owner != null) {
                tracker.setPlacer(event.getBlock(), tracker.getOwner((FallingBlock) event.getEntity()));
                tracker.setOwner((FallingBlock) event.getEntity(), null);
            }
        }
    }
}
