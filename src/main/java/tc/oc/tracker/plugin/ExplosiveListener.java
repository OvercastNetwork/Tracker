package tc.oc.tracker.plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

// FIXME: code copy & pasted from PGM -- needs to be reworked
public class ExplosiveListener implements Listener {
    private final Map<Block, Player> tntBlockPlayerMap = new HashMap<Block, Player>();
    private static final Map<Entity, Player> tntEntityPlayerMap = new HashMap<Entity, Player>();

    private final ExplosiveTracker tracker;

    public ExplosiveListener(ExplosiveTracker tracker) {
        this.tracker = tracker;
    }

    public static @Nullable Player getTNTOwner(Entity tnt) {
        return tntEntityPlayerMap.get(tnt);
    }

    public static void setTNTOwner(Player owner, Entity tnt) {
        tntEntityPlayerMap.put(tnt, owner);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.TNT) {
            this.tntBlockPlayerMap.put(event.getBlock(), event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(this.tntBlockPlayerMap.containsKey(event.getBlock())) {
            this.tntBlockPlayerMap.remove(event.getBlock());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        Map<Block, Player> updated = new HashMap<Block, Player>();
        List<Block> toremove = new LinkedList<Block>();
        for(Block block : event.getBlocks()) {
            Player player = this.tntBlockPlayerMap.get(block);
            if(player != null) {
                toremove.add(block);
                updated.put(block.getRelative(event.getDirection()), player);
            }
        }
        for(Block block : toremove) {
            this.tntBlockPlayerMap.remove(block);
        }
        this.tntBlockPlayerMap.putAll(updated);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if(event.isSticky()) {
            Block newBlock = event.getBlock().getRelative(event.getDirection());
            Block oldBlock = newBlock.getRelative(event.getDirection());
            Player player = this.tntBlockPlayerMap.get(oldBlock);
            if(player != null) {
                this.tntBlockPlayerMap.remove(oldBlock);
                this.tntBlockPlayerMap.put(newBlock, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTNTIgnite(ExplosionPrimeEvent event) {
        Block block = event.getEntity().getWorld().getBlockAt(event.getEntity().getLocation());
        Player player = this.tntBlockPlayerMap.get(block);
        if(player != null && event.getEntity() instanceof TNTPrimed) {
            setTNTOwner(player, event.getEntity());
        }
    }
}
