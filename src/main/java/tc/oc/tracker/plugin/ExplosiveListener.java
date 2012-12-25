package tc.oc.tracker.plugin;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import tc.oc.tracker.ExplosiveTracker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ExplosiveListener implements Listener {
    private final ExplosiveTracker tracker;

    public ExplosiveListener(ExplosiveTracker tracker) {
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.TNT) {
            this.tracker.setPlacer(event.getBlock(), event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        this.tracker.setPlacer(event.getBlock(), null);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        Map<Block, Player> updated = Maps.newHashMap();
        List<Block> toremove = Lists.newLinkedList();

        for(Block block : event.getBlocks()) {
            Player placer = this.tracker.getPlacer(block);
            if(placer != null) {
                toremove.add(block);
                updated.put(block.getRelative(event.getDirection()), placer);
            }
        }

        for(Block block : toremove) {
            Player newPlacer = updated.remove(block);
            this.tracker.setPlacer(block, newPlacer);
        }

        for(Map.Entry<Block, Player> entry : updated.entrySet()) {
            this.tracker.setPlacer(entry.getKey(), entry.getValue());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if(event.isSticky()) {
            Block newBlock = event.getBlock().getRelative(event.getDirection());
            Block oldBlock = newBlock.getRelative(event.getDirection());
            Player player = this.tracker.getPlacer(oldBlock);
            if(player != null) {
                this.tracker.setPlacer(oldBlock, null);
                this.tracker.setPlacer(newBlock, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTNTIgnite(ExplosionPrimeEvent event) {
        if(event.getEntity() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            Block block = event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation());
            if(block != null) {
                Player placer = this.tracker.setPlacer(block, null);
                if(placer != null) {
                    this.tracker.setOwner(tnt, placer);
                }
            }
        }
    }
}
