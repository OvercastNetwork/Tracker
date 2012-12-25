package tc.oc.tracker.event;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

import tc.oc.tracker.Lifetime;

import com.google.common.base.Preconditions;

public class EntityDeathEvent extends EntityEvent {
    private final Lifetime lifetime;

    public EntityDeathEvent(@Nonnull Entity entity, @Nonnull Lifetime lifetime) {
        super(entity);

        Preconditions.checkNotNull(lifetime, "lifetime");

        this.lifetime = lifetime;
    }

    public @Nonnull Lifetime getLifetime() {
        return this.lifetime;
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
