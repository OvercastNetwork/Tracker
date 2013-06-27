package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.DispensedProjectileDamageInfo;
import tc.oc.tracker.trackers.DispenserTracker;
import tc.oc.tracker.trackers.ProjectileDistanceTracker;

public class DispensedProjectileDamageResolver implements DamageResolver {
    private final ProjectileDistanceTracker projectileDistanceTracker;
    private final DispenserTracker dispenserTracker;

    public DispensedProjectileDamageResolver(ProjectileDistanceTracker projectileDistanceTracker, DispenserTracker dispenserTracker) {
        this.projectileDistanceTracker = projectileDistanceTracker;
        this.dispenserTracker = dispenserTracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                Location launchLocation = this.projectileDistanceTracker.getLaunchLocation(projectile);
                Double projectileDistance = null;
                OfflinePlayer dispenserOwner = dispenserTracker.getOwner(projectile);


                if(launchLocation != null) projectileDistance = event.getEntity().getLocation().distance(launchLocation);

                return new DispensedProjectileDamageInfo(projectile, projectile.getShooter(), projectileDistance, dispenserOwner);
            }
        }
        return null;
    }
}
