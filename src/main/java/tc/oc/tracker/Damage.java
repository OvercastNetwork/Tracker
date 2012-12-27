package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.joda.time.Instant;

/**
 * Represents a single instance of damage to an entity.
 *
 * Implementations should be immutable.
 */
public interface Damage {
    /**
     * Gets the amount of hearts of damage that were inflicted.
     *
     * Contract specifies that the result is 0 or greater.
     *
     * @return Hearts of damage inflicted
     */
    int getHearts();

    /**
     * Gets the location where the damage occurred.
     *
     * Contract specifies that the result is never null.
     *
     * @return Location of damage
     */
    @Nonnull Location getLocation();

    /**
     * Gets the time that the damage occurred.
     *
     * Contract specifies that the result is never null.
     *
     * @return Time of damage
     */
    @Nonnull Instant getTime();

    /**
     * Gets the resolved living damager if there is one.
     *
     * Contract specifies nothing about this field.
     *
     * @return Resolved damager or null if there is not one
     */
    @Nullable LivingEntity getResolvedDamager();
}
