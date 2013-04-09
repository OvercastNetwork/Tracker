package tc.oc.tracker.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import tc.oc.tracker.trackers.ProjectileDistanceTracker;

public class ProjectileDistanceListener implements Listener {
    private final ProjectileDistanceTracker tracker;

    public ProjectileDistanceListener(ProjectileDistanceTracker tracker) {
        this.tracker = tracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if(!this.tracker.isEnabled(event.getEntity().getWorld())) return;

        this.tracker.setLaunchLocation(event.getEntity(), event.getEntity().getLocation());
    }
}
