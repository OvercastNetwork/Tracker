package tc.oc.tracker.damage;

import javax.annotation.Nonnull;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import tc.oc.tracker.base.AbstractDamageInfo;

import com.google.common.base.Preconditions;

public class BukkitDamageInfo extends AbstractDamageInfo {
    public BukkitDamageInfo(@Nonnull DamageCause cause) {
        super(null);

        Preconditions.checkNotNull(cause, "damage cause");

        this.cause = cause;
    }

    public @Nonnull DamageCause getCause() {
        return this.cause;
    }

    private final @Nonnull DamageCause cause;
}
