package tc.oc.tracker.damage.base;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.VoidDamageInfo;

public class SimpleVoidDamageInfo extends AbstractDamageInfo implements VoidDamageInfo {
    public SimpleVoidDamageInfo(@Nullable LivingEntity resolvedDamager) {
        super(resolvedDamager);
    }
}
