package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

public class DispensedProjectileDamageInfo extends ProjectileDamageInfo {

    public DispensedProjectileDamageInfo(Projectile projectile, LivingEntity resolvedDamager, Double projectileDistance, OfflinePlayer blockOwner, BlockState blockState) {
        super(projectile, resolvedDamager, projectileDistance);
        this.blockOwner = blockOwner;
        this.blockState = blockState;
    }

    public @Nullable OfflinePlayer getBlockOwner() {
        return this.blockOwner;
    }

    public @Nullable BlockState getBlockState() {
        return this.blockState;
    }

    private final @Nullable OfflinePlayer blockOwner;
    private final @Nullable BlockState blockState;

    @Override
    public @Nonnull String toString() {
        return "DispensedProjectileDamageInfo{shooter=" + this.resolvedDamager + ",projectile=" + this.projectile + ",distance=" + this.projectileDistance + ",blockOwner=" + this.blockOwner + ",blockState=" + this.blockState + "}";
    }
}
