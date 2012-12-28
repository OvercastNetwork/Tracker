package tc.oc.tracker;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.joda.time.Instant;

import tc.oc.tracker.base.SimpleDamage;
import tc.oc.tracker.damage.resolvers.DamageAPIResolver;

import com.google.common.base.Preconditions;

/**
 * Provides convenient static API calls for other plugins to use.
 */
public final class DamageAPI {
    private DamageAPI() { }

    /**
     * Calls {@link #inflictDamage} with force off.
     */
    public static @Nullable Damage inflictDamage(@Nonnull LivingEntity entity, int hearts, @Nonnull DamageInfo info) {
        return inflictDamage(entity, hearts, info, false);
    }

    /**
     * Inflicts the given damage on an entity.
     *
     * This method will call the appropriate damage method and fire an {@link EntityDamageEvent}.
     *
     * @param entity Entity to inflict damage upon
     * @param hearts Amount of half-hearts of damage to inflict
     * @param info {@link DamageInfo} object that details the type of damage
     * @param force Indicates whether this method should respect cancellations
     * @return the final {@link Damage} object or null if the damage was cancelled
     *
     * @throws NullPointerException if entity or info is null
     * @throws IllegalArgumentExcpetion if hearts is less than zero
     */
    public static @Nullable Damage inflictDamage(@Nonnull LivingEntity entity, int hearts, @Nonnull DamageInfo info, boolean force) {
        Preconditions.checkNotNull(entity, "living entity");
        Preconditions.checkArgument(hearts >= 0, "hearts must be greater than or equal to zero");
        Preconditions.checkNotNull(info, "damage info");

        EntityDamageEvent event = new EntityDamageEvent(entity, DamageCause.CUSTOM, hearts);
        setAPIDamage(event, info);

        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled() && !force) {
            return null;
        }

        entity.damage(event.getDamage());

        setAPIDamage(event, null);

        return getDamage(event);
    }

    private static @Nonnull Map<EntityDamageEvent, DamageInfo> apiDamageMapping = new HashMap<EntityDamageEvent, DamageInfo>();

    /**
     * Fetches the given {@link DamageInfo} object specified by
     * {@link #inflictDamage} so it can be fetched by the
     * {@link DamageAPIResolver} when plugins who listen to the Bukkit event
     * ask for it.
     *
     * Intended for internal use only, so this method is not guaranteed to stay
     * the same through stable version releases.
     *
     * @param event EntityDamageEvent to fetch the given damage for
     * @return Stored damage info or null if none is stored
     *
     * @throws NullPointerException if event is null
     */
    public static @Nullable DamageInfo getAPIDamage(@Nonnull EntityDamageEvent event) {
        Preconditions.checkNotNull(event, "entity damage event");

        return apiDamageMapping.get(event);
    }

    /**
     * Sets the API damage info that corresponds to the specified event.
     *
     * Intended for internal use only, so this method is not guaranteed to stay
     * the same through stable version releases.
     *
     * @param event Specified event
     * @param damage Damage info that describes the damage or null to clear the
     *               store information
     *
     * @throws NullPointerException if event is null
     */
    public static void setAPIDamage(@Nonnull EntityDamageEvent event, @Nullable DamageInfo info) {
        Preconditions.checkNotNull(event, "entity damage event");

        if(info != null) {
            apiDamageMapping.put(event, info);
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

        DamageInfo info = DamageResolvers.getManager().resolve(entity, lifetime, event);

        // FIXME: should not return the current time
        return new SimpleDamage(event.getDamage(), event.getEntity().getLocation(), Instant.now(), info);
    }
}
