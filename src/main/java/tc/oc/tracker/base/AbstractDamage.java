package tc.oc.tracker.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.joda.time.Instant;

import tc.oc.tracker.Damage;

import com.google.common.base.Preconditions;

public abstract class AbstractDamage implements Damage {
    private final int hearts;
    private final @Nonnull Location location;
    private final @Nonnull Instant time;
    private final @Nullable LivingEntity resolvedDamager;

    public AbstractDamage(@Nonnull Damage base) {
        this(base.getHearts(), base.getLocation(), base.getTime(), base.getResolvedDamager());
    }

    public AbstractDamage(int hearts, @Nonnull Location location, @Nonnull Instant time, @Nullable LivingEntity resolvedDamager) {
        Preconditions.checkArgument(hearts > 0, "hearts must be greater than zero");
        Preconditions.checkNotNull(location, "location");
        Preconditions.checkNotNull(time, "time");
        Preconditions.checkNotNull(resolvedDamager, "resolved damager");

        this.hearts = hearts;
        this.location = location;
        this.time = time;
        this.resolvedDamager = resolvedDamager;
    }

    public int getHearts() {
        return this.hearts;
    }

    public @Nonnull Location getLocation() {
        return this.location;
    }

    public @Nonnull Instant getTime() {
        return this.time;
    }

    public @Nullable LivingEntity getResolvedDamager() {
        return this.resolvedDamager;
    }
}
