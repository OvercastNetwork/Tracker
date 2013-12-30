package tc.oc.tracker.damage.base;

import org.bukkit.entity.LivingEntity;
import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.FireEnchantDamageInfo;

import javax.annotation.Nullable;

public class SimpleFireEnchantDamageInfo extends AbstractDamageInfo implements FireEnchantDamageInfo {
    public SimpleFireEnchantDamageInfo(@Nullable LivingEntity resolvedDamager) {
        super(resolvedDamager);
    }
}
