package tc.oc.tracker.plugin;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.plugin.Plugin;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.trackers.FireEnchantTracker;

import java.util.Map;

public class FireEnchantListener implements Listener {
    FireEnchantTracker fireTracker;
    private final Plugin plugin;
    private final TickTimer tickTimer;

    Map<Player, Integer> schedulers = Maps.newHashMap();

    public FireEnchantListener(FireEnchantTracker fireTracker, Plugin plugin, TickTimer tickTimer) {
        this.fireTracker = fireTracker;
        this.plugin = plugin;
        this.tickTimer = tickTimer;
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (fireTracker.hasOriginalDamager(player)) {
                cancelRemoval(player);
                scheduleRemoval(event.getDuration(), player);
            }
        }
    }

    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getCombuster() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getCombuster();
            this.fireTracker.setOriginalDamager(arrow.getShooter(), (Player) event.getEntity());

        } else if (event.getCombuster() instanceof Player) {
            LivingEntity damager = (LivingEntity) event.getCombuster();
            this.fireTracker.setOriginalDamager(damager, (Player) event.getEntity());
        }
    }

    /**
     * Cancels the scheduler for the player
     *
     * @param player player to cancel scheduler for
     */
    private void cancelRemoval(Player player) {
        if (schedulers.containsKey(player)) {
            Bukkit.getScheduler().cancelTask(schedulers.get(player));
        }
    }

    /**
     * Create a scheduler that will remove the fire attack
     *
     * @param duration length of fire attack
     * @param player   player to schedule removal for
     */
    private void scheduleRemoval(int duration, final Player player) {
        final long start = this.tickTimer.getTicks();
        final long end = start + (duration * 20);
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                if (end == FireEnchantListener.this.tickTimer.getTicks() || player.isDead()) {
                    fireTracker.removeOriginalDamager(player);
                    cancelRemoval(player);
                }
            }
        }, 0L, 1L);

        schedulers.put(player, id);
    }
}