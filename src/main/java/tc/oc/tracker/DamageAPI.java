package tc.oc.tracker;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import tc.oc.tracker.plugin.DamageAPIHelper;

import com.google.common.base.Preconditions;

/**
 * Provides convenient static API calls for other plugins to use.
 */
public final class DamageAPI {
    private DamageAPI() { }

    /**
     * Inflicts the given damage on an entity.
     *
     * This method will call the appropriate damage method and fire an {@link EntityDamageEvent}.
     *
     * @param entity Entity to inflict damage upon
     * @param damage Amount of half-hearts of damage to inflict
     * @param info {@link DamageInfo} object that details the type of damage
     * @return the final {@link Damage} object (never null)
     *
     * @throws NullPointerException if entity or info is null
     */
    public static @Nonnull Damage inflictDamage(@Nonnull LivingEntity entity, int damage, @Nonnull DamageInfo info) {
        Preconditions.checkNotNull(entity, "living entity");
        Preconditions.checkArgument(damage >= 0, "damage must be greater than or equal to zero");
        Preconditions.checkNotNull(info, "damage info");

        DamageAPIHelper helper = DamageAPIHelper.get();

        EntityDamageEvent event = new EntityDamageEvent(entity, DamageCause.CUSTOM, (double) damage);
        helper.setEventDamageInfo(event, info);

        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return null;
        }

        entity.damage(event.getDamage());

        helper.setEventDamageInfo(event, null);

        return helper.getOurEvent(event).toDamageObject();
    }
}
