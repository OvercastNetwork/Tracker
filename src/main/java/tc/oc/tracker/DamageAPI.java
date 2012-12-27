package tc.oc.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import tc.oc.tracker.resolvers.DamageAPIResolver;

import com.google.common.base.Preconditions;

/**
 * Provides convenient static API calls for other plugins to use.
 */
public final class DamageAPI {
    private DamageAPI() { }

    /**
     * Calls {@link #inflictDamage} with force off.
     */
    public static boolean inflictDamage(@Nonnull LivingEntity entity, @Nonnull Damage damage) {
        return inflictDamage(entity, damage, false);
    }

    /**
     * Inflicts the given damage on an entity.
     *
     * This method will call the appropriate damage method and fire an {@link EntityDamageEvent}.
     *
     * @param entity Entity to inflict damage upon
     * @param damage Damage object that describes this bit of damage
     * @param force Indicates whether this method should respect cancellations
     * @return
     */
    public static boolean inflictDamage(@Nonnull LivingEntity entity, @Nonnull Damage damage, boolean force) {
        Preconditions.checkNotNull(entity, "living entity");
        Preconditions.checkNotNull(damage, "damage");

        EntityDamageEvent event = new EntityDamageEvent(entity, DamageCause.CUSTOM, damage.getHearts());
        setAPIDamage(event, damage);

        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled() && !force) {
            return false;
        }

        entity.damage(damage.getHearts());

        setAPIDamage(event, null);

        return true;
    }

    private static @Nonnull Map<EntityDamageEvent, Damage> apiDamageMapping = new HashMap<EntityDamageEvent, Damage>();

    /**
     * Fetches the given {@link Damage} object specified by
     * {@link #inflictDamage} so it can be fetched by the
     * {@link DamageAPIResolver} when plugins who listen to the Bukkit event
     * ask for it.
     *
     * Intended for internal use only, so this method is not guaranteed to stay
     * the same through stable version releases.
     *
     * @param event EntityDamageEvent to fetch the given damage for
     * @return Stored damage instance of null if none is stored
     *
     * @throws NullPointerException if event is null
     */
    public static @Nullable Damage getAPIDamage(@Nonnull EntityDamageEvent event) {
        Preconditions.checkNotNull(event, "entity damage event");

        return apiDamageMapping.get(event);
    }

    /**
     * Sets the API damage that corresponds to the specified event.
     *
     * Intended for internal use only, so this method is not guaranteed to stay
     * the same through stable version releases.
     *
     * @param event Specified event
     * @param damage Damage object that represents the damage for the event
     *
     * @throws NullPointerException if event is null
     */
    public static void setAPIDamage(@Nonnull EntityDamageEvent event, @Nullable Damage damage) {
        Preconditions.checkNotNull(event, "entity damage event");

        if(damage != null) {
            apiDamageMapping.put(event, damage);
        } else {
            apiDamageMapping.remove(event);
        }
    }

    /**
     * Gets the {@link Damage} instance that corresponds to the specified event.
     *
     * @param event Specified event
     * @return Damage instance describing the event (will never be null)
     *
     * @throws NullPointerException if event is null
     * @throws IllegalArgumentException if the event entity is not living
     */
    public static @Nonnull Damage getDamage(@Nonnull EntityDamageEvent event) {
        Preconditions.checkNotNull(event, "entity damage event");
        Preconditions.checkArgument(event.getEntity() instanceof LivingEntity, "event entity must be living");

        LivingEntity entity = (LivingEntity) event.getEntity();
        Lifetime lifetime = Lifetimes.getLifetime(entity);

        return DamageResolvers.getManager().resolve(entity, lifetime, event);
    }
}
