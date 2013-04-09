package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.ProjectileDamageInfo;
import tc.oc.tracker.trackers.DispenserTracker;
import tc.oc.tracker.trackers.ProjectileDistanceTracker;

public class ProjectileDamageResolver implements DamageResolver {
    private final ProjectileDistanceTracker projectiledistancetracker;
    private final DispenserTracker dispensertracker;

    public ProjectileDamageResolver(ProjectileDistanceTracker projectiledistancetracker, DispenserTracker dispensertracker) {
        this.projectiledistancetracker = projectiledistancetracker;
        this.dispensertracker = dispensertracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                Location launchLocation = this.projectiledistancetracker.getLaunchLocation(projectile);
                Double projectileDistance = null;
                OfflinePlayer blockOwner = null;
                BlockState blockState = dispensertracker.getOwner(projectile);

                if (blockState != null) blockOwner = dispensertracker.getPlacer(blockState.getBlock());

                if (launchLocation != null) projectileDistance = event.getEntity().getLocation().distance(launchLocation);

                return new ProjectileDamageInfo(projectile, projectile.getShooter(), projectileDistance, blockOwner, blockState);
            }
        }
        return null;
    }
}
