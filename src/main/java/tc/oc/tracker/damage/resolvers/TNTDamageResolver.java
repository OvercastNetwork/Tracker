package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.TNTDamageInfo;
import tc.oc.tracker.trackers.ExplosiveTracker;

public class TNTDamageResolver implements DamageResolver {
    private final ExplosiveTracker tracker;

    public TNTDamageResolver(ExplosiveTracker tracker) {
        this.tracker = tracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull Entity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof TNTPrimed) {
                TNTPrimed tnt = (TNTPrimed) event.getDamager();
                Player owner = this.tracker.getOwner(tnt);

                return new TNTDamageInfo(tnt, owner);
            }
        }

        return null;
    }

}
