package tc.oc.tracker.event;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

import tc.oc.tracker.Damage;
import tc.oc.tracker.Lifetime;

import com.google.common.base.Preconditions;

/**
 * Called when an entity undergoes some type of damage.
 */
public class EntityDamageEvent extends EntityEvent {
    private final @Nonnull Lifetime lifetime;
    private final @Nonnull Damage damage;

    public EntityDamageEvent(@Nonnull Entity entity, @Nonnull Lifetime lifetime, @Nonnull Damage damage) {
        super(entity);

        Preconditions.checkNotNull(lifetime, "lifetime");
        Preconditions.checkNotNull(damage, "damage");

        this.lifetime = lifetime;
        this.damage = damage;
    }

    public @Nonnull Lifetime getLifetime() {
        return this.lifetime;
    }

    public @Nonnull Damage getDamage() {
        return this.damage;
    }

    // Bukkit event junk
    public static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
