package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.GravityDamageInfo;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.trackers.base.gravity.Fall;
import tc.oc.tracker.trackers.base.gravity.SimpleGravityKillTracker;

import com.google.common.base.Preconditions;

public class GravityDamageResolver implements DamageResolver {
    public GravityDamageResolver(@Nonnull SimpleGravityKillTracker tracker) {
        Preconditions.checkNotNull(tracker, "tracker");
        this.tracker = tracker;
    }

    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(!(entity instanceof Player)) return null;
        Player victim = (Player) entity;
        Fall fall = this.tracker.getCausingFall(victim, damageEvent.getCause());
        if(fall != null) {
            return new GravityDamageInfo(fall.attacker, fall.cause, fall.from);
        } else {
            return null;
        }
    }

    private final @Nonnull SimpleGravityKillTracker tracker;
}
