package tc.oc.tracker;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import com.google.common.base.Preconditions;

public final class DamageAPI {
    private DamageAPI() { }

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
