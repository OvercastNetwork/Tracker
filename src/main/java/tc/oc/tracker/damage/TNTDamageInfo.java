package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;

public class TNTDamageInfo extends ExplosiveDamageInfo {
    public TNTDamageInfo(@Nonnull TNTPrimed explosive, @Nullable LivingEntity resolvedDamager, @Nullable OfflinePlayer blockOwner, @Nullable BlockState blockState) {
        super(explosive, resolvedDamager);

        this.blockOwner = blockOwner;
        this.blockState = blockState;
    }

    @Override
    public @Nonnull TNTPrimed getExplosive() {
        return (TNTPrimed) this.explosive;
    }

    private final @Nullable OfflinePlayer blockOwner;
    private final @Nullable BlockState blockState;

    @Override
    public @Nonnull String toString() {
        return "TNTDamageInfo{explosive=" + this.explosive + ",damager=" + this.resolvedDamager + ",blockOwner=" + this.blockOwner + ",blockState=" + this.blockState + "}";
    }
}
