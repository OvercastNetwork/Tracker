package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.OwnedMobDamageInfo;
import tc.oc.tracker.trackers.OwnedMobTracker;

public class OwnedMobDamageResolver implements DamageResolver {
    private final OwnedMobTracker ownedMobTracker;

    public OwnedMobDamageResolver(OwnedMobTracker ownedMobTracker) {
        this.ownedMobTracker = ownedMobTracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof Projectile) {
                if(!(((Projectile) event.getDamager()).getShooter() instanceof Player)) {
                    LivingEntity mob = ((Projectile) event.getDamager()).getShooter();
                    OfflinePlayer mobOwner = ownedMobTracker.getOwner(mob);

                    return new OwnedMobDamageInfo(mob, mobOwner);
                }
            } else if(!(event.getDamager() instanceof Player) && event.getDamager() instanceof LivingEntity) {
                LivingEntity mob = (LivingEntity) event.getDamager();
                OfflinePlayer mobOwner = ownedMobTracker.getOwner(mob);

                return new OwnedMobDamageInfo(mob, mobOwner);
            }
        }
        return null;
    }
}
