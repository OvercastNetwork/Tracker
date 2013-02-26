package tc.oc.tracker.damage.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.BlockDamageInfo;

import com.google.common.base.Preconditions;

public class AbstractBlockDamageInfo extends AbstractDamageInfo implements BlockDamageInfo {
    public AbstractBlockDamageInfo(@Nullable LivingEntity resolvedDamager, @Nonnull BlockState blockDamager) {
        super(resolvedDamager);

        Preconditions.checkNotNull(blockDamager, "block damager");

        this.blockDamager = blockDamager;
    }

    public @Nonnull BlockState getBlockDamager() {
        return this.blockDamager;
    }

    private final @Nonnull BlockState blockDamager;
}
