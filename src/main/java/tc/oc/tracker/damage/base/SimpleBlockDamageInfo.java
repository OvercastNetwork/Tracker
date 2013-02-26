package tc.oc.tracker.damage.base;

import javax.annotation.Nonnull;

import org.bukkit.block.BlockState;

public class SimpleBlockDamageInfo extends AbstractBlockDamageInfo {
    public SimpleBlockDamageInfo(@Nonnull BlockState blockDamager) {
        super(null, blockDamager);
    }
}
