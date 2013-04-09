package tc.oc.tracker.trackers.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Projectile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import tc.oc.tracker.base.AbstractTracker;
import tc.oc.tracker.trackers.ProjectileDistanceTracker;

public class SimpleProjectileDistanceTracker extends AbstractTracker implements ProjectileDistanceTracker {
    private final HashMap<Projectile, Location> projectileLaunchLocations = Maps.newHashMap();

    public boolean hasLaunchLocation(@Nonnull Projectile projectile) {
        Preconditions.checkNotNull(projectile, "projectile entity");

        return this.projectileLaunchLocations.containsKey(projectile);
    }

    public @Nullable Location getLaunchLocation(@Nonnull Projectile projectile) {
        Preconditions.checkNotNull(projectile, "projectile entity");

        return this.projectileLaunchLocations.get(projectile);
    }

    public @Nullable Location setLaunchLocation(@Nonnull Projectile projectile, @Nullable Location location) {
        Preconditions.checkNotNull(projectile, "projectile entity");

        if(location != null) {
            return this.projectileLaunchLocations.put(projectile, location);
        } else {
            return this.projectileLaunchLocations.remove(projectile);
        }
    }

    public void clear(World world) {
        // clear information about projectile launch locations in that world
        for(Iterator<Map.Entry<Projectile, Location>> it = this.projectileLaunchLocations.entrySet().iterator(); it.hasNext(); ) {
            Projectile projectile = it.next().getKey();
            if(projectile.getWorld().equals(world)) {
                it.remove();
            }
        }
    }
}
