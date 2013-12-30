package tc.oc.tracker.trackers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import tc.oc.tracker.Tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface FireEnchantTracker extends Tracker {
    /**
     * Whether or not the player was recently
     * damaged by a fire enchant weapon
     *
     * @param player player to check
     * @return whether or not a recent attack occurred
     */
    boolean hasOriginalDamager(@Nonnull Player player);

    /**
     * Gets the current damager for a player
     *
     * @param player player to get damager for
     * @return LivingEntity that last damaged the player with fire
     */
    @Nullable LivingEntity getOriginalDamager(@Nonnull Player player);

    /**
     * Sets the last LivingEntity to damage
     * the player with a fire enchanted weapon
     *
     * @param damager LivingEntity that last damaged the player
     * @param damaged Player that was damaged
     * @return the living entity that damaged the player
     */
    @Nullable LivingEntity setOriginalDamager(@Nullable LivingEntity damager, @Nonnull Player damaged);

    /**
     * Removes the last damager from the player,
     *
     * @param player player to remove damager from
     */
    void removeOriginalDamager(@Nonnull Player player);

}
