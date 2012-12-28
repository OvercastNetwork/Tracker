package tc.oc.tracker;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

public interface DamageInfo {
    @Nullable LivingEntity getResolvedDamager();
}
