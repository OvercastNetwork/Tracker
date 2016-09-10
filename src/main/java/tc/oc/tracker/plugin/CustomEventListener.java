package tc.oc.tracker.plugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import tc.oc.tracker.event.BlockDispenseEntityEvent;
import tc.oc.tracker.event.BlockFallEvent;
import tc.oc.tracker.event.PlayerCoarseMoveEvent;
import tc.oc.tracker.event.PlayerOnGroundEvent;
import tc.oc.tracker.util.EventUtil;

public class CustomEventListener implements Listener {
    private static final String METADATA_GROUND = "tracker.on-ground";

    private final TrackerPlugin plugin;

    public CustomEventListener(TrackerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerOnGroundCall(PlayerCoarseMoveEvent event) {
        boolean groundBefore = event.getPlayer().isOnGround();
        boolean groundAfter = event.getPlayer().isOnGround();

        // Fetch previous on ground state
        if (event.getPlayer().hasMetadata(METADATA_GROUND)) {
            groundBefore = event.getPlayer().getMetadata(METADATA_GROUND).get(0).asBoolean();
        }

        // If on ground state changed, store it and call the event.
        if (groundBefore != groundAfter) {
            event.getPlayer().setMetadata(METADATA_GROUND, new FixedMetadataValue(this.plugin, groundAfter));

            PlayerOnGroundEvent call = new PlayerOnGroundEvent(event.getPlayer(), groundAfter);

            for (EventPriority priority : EventPriority.values())
                EventUtil.callEvent(call, PlayerOnGroundEvent.getHandlerList(), priority);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockFallCall(EntitySpawnEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            Block block = fallingBlock.getSourceLoc().getBlock();

            BlockFallEvent call = new BlockFallEvent(block, fallingBlock);

            for (EventPriority priority : EventPriority.values())
                EventUtil.callEvent(call, BlockFallEvent.getHandlerList(), priority);
            call.setCancelled(event.isCancelled());

            if (call.isCancelled()) {
                block.setType(fallingBlock.getMaterial());
                block.setData(fallingBlock.getBlockData());
                fallingBlock.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockDispenseEntityCall(EntitySpawnEvent event) {
        Block block = event.getLocation().getBlock();

        if (block.getType() == Material.DISPENSER) {
            BlockDispenseEntityEvent call = new BlockDispenseEntityEvent(block, event.getEntity());
            call.setCancelled(event.isCancelled());

            for (EventPriority priority : EventPriority.values())
                EventUtil.callEvent(call, BlockDispenseEntityEvent.getHandlerList(), priority);

            event.setCancelled(call.isCancelled());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCoarseMoveCall(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX())
            if (from.getBlockY() == to.getBlockY())
                if (from.getBlockZ() == to.getBlockZ())
                    return;

        PlayerCoarseMoveEvent call = new PlayerCoarseMoveEvent(event.getPlayer(), from, to);
        call.setCancelled(event.isCancelled());

        for (EventPriority priority : EventPriority.values())
            EventUtil.callEvent(call, PlayerCoarseMoveEvent.getHandlerList(), priority);

        event.setCancelled(call.isCancelled());
        event.setFrom(call.getFrom());
        event.setTo(call.getTo());
    }
}
