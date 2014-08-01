package tc.oc.tracker.trackers.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import tc.oc.tracker.base.AbstractTracker;
import tc.oc.tracker.trackers.FireEnchantTracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class SimpleFireEnchantTracker extends AbstractTracker implements FireEnchantTracker {
    private final Map<Player, LivingEntity> fireDamages = Maps.newHashMap();

    /**
     * Whether or not the player was recently
     * damaged by a fire enchant weapon
     *
     * @param player player to check
     * @return whether or not a recent attack occurred
     */
    public boolean hasOriginalDamager(@Nonnull Player player) {
        Preconditions.checkNotNull(player, "player");

        return fireDamages.containsKey(player);
    }

    /**
     * Gets the current damager for a player
     *
     * @param player player to get damager for
     * @return LivingEntity that last damaged the player with fire
     */
    @Nullable public LivingEntity getOriginalDamager(@Nonnull Player player) {
        Preconditions.checkNotNull(player, "player");

        return fireDamages.get(player);
    }

    /**
     * Sets the last LivingEntity to damage
     * the player with a fire enchanted weapon
     *
     * @param damager LivingEntity that last damaged the player
     * @param damaged Player that was damaged
     * @return the living entity that damaged the player
     */
    @Nullable public LivingEntity setOriginalDamager(@Nullable LivingEntity damager, @Nonnull Player damaged) {
        if (damager == null) {
            return fireDamages.remove(damaged);
        } else {
            return fireDamages.put(damaged, damager);
        }
    }

    /**
     * Removes the last damager from the player,
     *
     * @param player player to remove damager from
     */
    public void removeOriginalDamager(@Nonnull Player player) {
        if (hasOriginalDamager(player)) {
            fireDamages.remove(player);
        }
    }

    public void clear(@Nonnull World world) {
        Preconditions.checkNotNull(world, "world");

        for (Iterator<Map.Entry<Player, LivingEntity>> it = this.fireDamages.entrySet().iterator(); it.hasNext(); ) {
            it.remove();
        }

    }
}
