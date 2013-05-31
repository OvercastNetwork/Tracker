package tc.oc.tracker.plugin;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSpawnEntityEvent;

import tc.oc.tracker.trackers.OwnedMobTracker;

public class OwnedMobListener implements Listener {
    private final OwnedMobTracker tracker;

    public OwnedMobListener(OwnedMobTracker tracker) {
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMonsterSpawn(PlayerSpawnEntityEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        if (event.getEntity() instanceof LivingEntity) {
            this.tracker.setOwner((LivingEntity) event.getEntity(), event.getPlayer());
        }
    }
}
