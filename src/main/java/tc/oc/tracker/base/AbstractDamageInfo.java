package tc.oc.tracker.base;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.DamageInfo;

public abstract class AbstractDamageInfo implements DamageInfo {
    protected AbstractDamageInfo(@Nullable LivingEntity resolvedDamager) {
        this.resolvedDamager = resolvedDamager;
    }

    public @Nullable LivingEntity getResolvedDamager() {
        return this.resolvedDamager;
    }

    protected final @Nullable LivingEntity resolvedDamager;
}
