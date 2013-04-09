package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.TNTDamageInfo;
import tc.oc.tracker.trackers.DispenserTracker;
import tc.oc.tracker.trackers.ExplosiveTracker;

public class TNTDamageResolver implements DamageResolver {
    private final ExplosiveTracker explosiveTracker;
    private final DispenserTracker dispenserTracker;

    public TNTDamageResolver(ExplosiveTracker explosiveTracker, DispenserTracker dispenserTracker) {
        this.explosiveTracker = explosiveTracker;
        this.dispenserTracker = dispenserTracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof TNTPrimed) {
                TNTPrimed tnt = (TNTPrimed) event.getDamager();
                Player owner = this.explosiveTracker.getOwner(tnt);
                OfflinePlayer blockOwner = null;
                BlockState blockState = dispenserTracker.getOwner(tnt);

                if(blockState != null) blockOwner = dispenserTracker.getPlacer(blockState.getBlock());

                return new TNTDamageInfo(tnt, owner, blockOwner, blockState);
            }
        }

        return null;
    }
}
