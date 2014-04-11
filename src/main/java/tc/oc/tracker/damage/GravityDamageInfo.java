package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.trackers.base.gravity.Fall;

import com.google.common.base.Preconditions;

public class GravityDamageInfo extends AbstractDamageInfo {
    public GravityDamageInfo(@Nullable LivingEntity resolvedDamager, @Nonnull Fall.Cause cause, @Nonnull Fall.From from) {
        super(resolvedDamager);

        Preconditions.checkNotNull(resolvedDamager, "damager");
        Preconditions.checkNotNull(cause, "cause");
        Preconditions.checkNotNull(from, "from");

        this.cause = cause;
        this.from = from;
    }

    public @Nonnull Fall.Cause getCause() {
        return this.cause;
    }

    public @Nonnull Fall.From getFrom() {
        return this.from;
    }

    private final @Nonnull Fall.Cause cause;
    private final @Nonnull Fall.From from;

    @Override
    public @Nonnull String toString() {
        return "GravityDamageInfo{damager=" + this.resolvedDamager + ",cause=" + this.cause + ",from=" + this.from + "}";
    }
}
