package tc.oc.tracker.plugin;

import javax.annotation.Nonnull;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerOnGroundEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.trackers.base.gravity.Fall;
import tc.oc.tracker.trackers.base.gravity.BrokenBlock;
import tc.oc.tracker.trackers.base.gravity.SimpleGravityKillTracker;
import tc.oc.tracker.util.PlayerBlockChecker;

import com.google.common.base.Preconditions;

public class GravityListener implements Listener {
    public GravityListener(@Nonnull TrackerPlugin plugin, @Nonnull SimpleGravityKillTracker tracker) {
        Preconditions.checkNotNull(plugin, "plugin");
        Preconditions.checkNotNull(tracker, "tracker");

        this.plugin = plugin;
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerOnGroundChanged(final PlayerOnGroundEvent event) {
        this.tracker.playerOnOrOffGround(event.getPlayer(), event.getOnGround());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(final PlayerMoveEvent event) {
        this.tracker.playerMoved(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        this.tracker.cancelFall(event.getEntity());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerGameModeChange(final PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.CREATIVE) {
            this.tracker.cancelFall(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        this.tracker.blockBroken(event.getBlock(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttack(final EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            this.tracker.playerAttacked((Player) event.getEntity(), event.getDamager());
        }
    }

    private final @Nonnull TrackerPlugin plugin;
    private final @Nonnull SimpleGravityKillTracker tracker;
}
