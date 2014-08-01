package tc.oc.tracker.damage.resolvers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.base.SimpleFireEnchantDamageInfo;
import tc.oc.tracker.trackers.FireEnchantTracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FireEnchantDamageResolver implements DamageResolver {
    FireEnchantTracker fireTracker;

    public FireEnchantDamageResolver(FireEnchantTracker fireTracker) {
        this.fireTracker = fireTracker;
    }

    @Nullable
    public DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        /**
         * Returns damager if caused by fire
         */
        if (damageEvent.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            if (damageEvent.getEntity() instanceof Player) {
                Player player = (Player) damageEvent.getEntity();
                if (this.fireTracker.hasOriginalDamager(player)) {
                    return new SimpleFireEnchantDamageInfo(this.fireTracker.getOriginalDamager(player));
                }
            }
        }
        return null;
    }
}