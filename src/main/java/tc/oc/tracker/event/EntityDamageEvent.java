package tc.oc.tracker.event;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.joda.time.Instant;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.Lifetime;

import com.google.common.base.Preconditions;

/**
 * Called when an entity undergoes some type of damage.
 */
public class EntityDamageEvent extends EntityEvent implements Cancellable {
    private final @Nonnull Lifetime lifetime;
    private int hearts;
    private final @Nonnull Location location;
    private final @Nonnull Instant time;
    private final @Nonnull DamageInfo info;
    private boolean cancelled = false;

    public EntityDamageEvent(@Nonnull Entity entity, @Nonnull Lifetime lifetime, int hearts, @Nonnull Location location, @Nonnull Instant time, @Nonnull DamageInfo info) {
        super(entity);

        Preconditions.checkNotNull(lifetime, "lifetime");
        Preconditions.checkArgument(hearts >= 0, "hearts must be greater than or equal to zero");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(time, "time");
        Preconditions.checkNotNull(info, "damage info");

        this.lifetime = lifetime;
        this.hearts = hearts;
        this.location = location.clone();
        this.time = time;
        this.info = info;
    }

    public int getHearts() {
        return this.hearts;
    }

    public void setHearts(int hearts) {
        Preconditions.checkArgument(hearts >= 0, "hearts must be greater than or equal to zero");

        this.hearts = hearts;
    }

    public @Nonnull Location getLocation() {
        return this.location;
    }

    public @Nonnull Instant getTime() {
        return this.time;
    }

    public @Nonnull Lifetime getLifetime() {
        return this.lifetime;
    }

    public @Nonnull DamageInfo getInfo() {
        return this.info;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
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
