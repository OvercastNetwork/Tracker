package tc.oc.tracker.damage.base;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.FallDamageInfo;

import com.google.common.base.Preconditions;

public class SimpleFallDamageInfo extends AbstractDamageInfo implements FallDamageInfo {
    public SimpleFallDamageInfo(@Nullable LivingEntity resolvedDamager, float fallDistance) {
        super(resolvedDamager);

        Preconditions.checkArgument(fallDistance >= 0, "fall distance must be >= 0");

        this.fallDistance = fallDistance;
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    private final float fallDistance;
}
