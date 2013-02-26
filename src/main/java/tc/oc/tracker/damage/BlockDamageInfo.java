package tc.oc.tracker.damage;

import javax.annotation.Nonnull;

import org.bukkit.block.BlockState;

import tc.oc.tracker.DamageInfo;

/**
 * Represents a damage caused by a specific block in the world.
 */
public interface BlockDamageInfo extends DamageInfo {
    /**
     * Gets the world block responsible for this damage.
     *
     * @return Snapshot of the damaging block
     */
    @Nonnull BlockState getBlockDamager();
}
