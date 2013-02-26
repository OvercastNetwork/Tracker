package tc.oc.tracker.damage.base;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.LavaDamageInfo;

public class SimpleLavaDamageInfo extends AbstractDamageInfo implements LavaDamageInfo {
    public SimpleLavaDamageInfo(@Nullable LivingEntity resolvedDamager) {
        super(resolvedDamager);
    }
}
