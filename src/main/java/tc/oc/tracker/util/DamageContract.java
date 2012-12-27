package tc.oc.tracker.util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.joda.time.Instant;

import tc.oc.tracker.Damage;

import com.google.common.base.Preconditions;

/**
 * Provides utility methods for checking the contract for Damage objects.
 *
 * @see Damage for contract
 */
public final class DamageContract {
    /**
     * Checks to ensure that the the specified damage object satisfies all
     * contracts.
     *
     * @param damage Specified damage object
     *
     * @throws NullPointerException if damage is null or one of the getters
     *                              returns an invalidly null object
     */
    public static void checkDamage(Damage damage) {
        Preconditions.checkNotNull(damage, "damage may not be null");
        checkHearts(damage.getHearts());
        checkLocation(damage.getLocation());
        checkTime(damage.getTime());
        checkResolvedDamager(damage.getResolvedDamager());
    }

    /**
     * Checks the damage hearts to ensure that they are greater than or equal
     * to 0.
     *
     * @param hearts Damage hearts
     *
     * @throws IllegalArgumentException if hearts is less than 0
     */
    public static void checkHearts(int hearts) {
        Preconditions.checkArgument(hearts >= 0, "damage hearts must be greater than or equal to 0");
    }

    /**
     * Checks the damage location to ensure that it is not null.
     *
     * @param location Damage location
     *
     * @throws NullPointerException if location is null
     */
    public static void checkLocation(Location location) {
        Preconditions.checkNotNull(location, "damage location may not be null");
    }

    /**
     * Checks the damage time to ensure that it is not null.
     *
     * @param time Damage time
     *
     * @throws NullPointerException if time is null
     */
    public static void checkTime(Instant time) {
        Preconditions.checkNotNull(time, "damage time may not be null");
    }

    /**
     * Checks the damage resolved damager
     *
     * Currently there is no contract specified for
     * {@link Damage#getResolvedDamager}, so this method does nothing.
     *
     * @param resolvedDamager Damage resolved damager
     */
    public static void checkResolvedDamager(LivingEntity resolvedDamager) {
        // no contract specified
    }
}
