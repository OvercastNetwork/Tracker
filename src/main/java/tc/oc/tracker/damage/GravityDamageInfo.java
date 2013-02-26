package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.trackers.base.gravity.Attack;

import com.google.common.base.Preconditions;

public class GravityDamageInfo extends AbstractDamageInfo {
    public GravityDamageInfo(@Nullable LivingEntity resolvedDamager, @Nonnull Attack.Cause cause, @Nonnull Attack.From from, boolean isClimbing, boolean isSwimming, boolean isInLava, boolean isOnGround) {
        super(resolvedDamager);

        Preconditions.checkNotNull(resolvedDamager, "damager");
        Preconditions.checkNotNull(cause, "cause");
        Preconditions.checkNotNull(from, "from");

        this.cause = cause;
        this.from = from;

        this.isClimbing = isClimbing;
        this.isSwimming = isSwimming;
        this.isInLava = isInLava;
        this.isOnGround = isOnGround;
        this.wasAttacked = !this.isSupported();
    }

    public @Nonnull Attack.Cause getCause() {
        return this.cause;
    }

    public @Nonnull Attack.From getFrom() {
        return this.from;
    }

    private final @Nonnull Attack.Cause cause;
    private final @Nonnull Attack.From from;

    public @Nonnull boolean isClimbing() {
        return this.isClimbing;
    }

    public @Nonnull boolean isSwimming() {
        return this.isSwimming;
    }

    public @Nonnull boolean isInLava() {
        return this.isInLava;
    }

    public @Nonnull boolean isOnGround() {
        return this.isOnGround;
    }

    public @Nonnull boolean wasAttacked() {
        return this.wasAttacked;
    }

    private final @Nonnull boolean isClimbing;
    private final @Nonnull boolean isSwimming;
    private final @Nonnull boolean isInLava;
    private final @Nonnull boolean isOnGround;
    private final @Nonnull boolean wasAttacked;

    private boolean isSupported() {
        return this.isClimbing || this.isSwimming || this.isOnGround;
    }

    @Override
    public @Nonnull String toString() {
        return "GravityDamageInfo{damager=" + this.resolvedDamager + ",cause=" + this.cause + ",from=" + this.from + ",isClimbing=" + this.isClimbing + ",isSwimming=" + this.isSwimming + ",isInLava=" + this.isInLava + ",isOnGround=" + this.isOnGround + ",wasAttacked=" + this.wasAttacked + "}";
    }
}
