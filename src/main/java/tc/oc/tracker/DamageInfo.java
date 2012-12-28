package tc.oc.tracker;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

/**
 * Provides more detailed information about a damage instance.
 *
 * Subclasses should be completely immutable.
 */
public interface DamageInfo {
    /**
     * Gets the living entity most responsible for this damage.
     *
     * @return Resolved damager or null if none exists
     */
    @Nullable LivingEntity getResolvedDamager();
}
