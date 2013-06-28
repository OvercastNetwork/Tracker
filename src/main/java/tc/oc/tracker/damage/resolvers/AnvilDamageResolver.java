package tc.oc.tracker.damage.resolvers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.AnvilDamageInfo;
import tc.oc.tracker.trackers.AnvilTracker;

public class AnvilDamageResolver implements DamageResolver {
    private final AnvilTracker anvilTracker;

    public AnvilDamageResolver(AnvilTracker anvilTracker) {
        this.anvilTracker = anvilTracker;
    }

    public DamageInfo resolve(LivingEntity entity, Lifetime lifetime, EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) damageEvent;

            if(event.getDamager() instanceof FallingBlock) {
                FallingBlock anvil = (FallingBlock) event.getDamager();
                OfflinePlayer offlineOwner = this.anvilTracker.getOwner(anvil);
                Player onlineOwner = null;

                if(offlineOwner != null) onlineOwner = offlineOwner.getPlayer();

                return new AnvilDamageInfo(anvil, onlineOwner, offlineOwner);
            }
        }

        return null;
    }

}
