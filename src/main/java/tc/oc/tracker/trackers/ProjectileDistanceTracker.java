package tc.oc.tracker.trackers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;

import tc.oc.tracker.Tracker;

public interface ProjectileDistanceTracker extends Tracker {
    boolean hasLaunchLocation(@Nonnull Projectile entity);

    @Nullable Location getLaunchLocation(@Nonnull Projectile projectile);

    @Nullable Location setLaunchLocation(@Nonnull Projectile projectile, @Nullable Location location);
}
