package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.base.AbstractDamageInfo;

import com.google.common.base.Preconditions;

public class TNTDamageInfo extends AbstractDamageInfo {
    public TNTDamageInfo(@Nonnull TNTPrimed tnt, @Nullable LivingEntity resolvedDamager, @Nullable OfflinePlayer blockOwner, @Nullable BlockState blockState) {
        super(resolvedDamager);

        Preconditions.checkNotNull(tnt, "tnt");

        this.tnt = tnt;
        this.blockOwner = blockOwner;
        this.blockState = blockState;
    }

    public @Nonnull TNTPrimed getTNT() {
        return this.tnt;
    }

    private final @Nonnull TNTPrimed tnt;
    private final @Nullable OfflinePlayer blockOwner;
    private final @Nullable BlockState blockState;

    @Override
    public @Nonnull String toString() {
        return "TNTDamageInfo{tnt=" + this.tnt + ",damager=" + this.resolvedDamager + ",blockOwner=" + this.blockOwner + ",blockState=" + this.blockState + "}";
    }
}
