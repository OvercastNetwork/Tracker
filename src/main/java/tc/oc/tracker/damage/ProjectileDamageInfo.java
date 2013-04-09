package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import tc.oc.tracker.base.AbstractDamageInfo;

import com.google.common.base.Preconditions;

public class ProjectileDamageInfo extends AbstractDamageInfo {
    public ProjectileDamageInfo(@Nonnull Projectile projectile, @Nullable LivingEntity resolvedDamager, @Nullable Double projectileDistance, @Nullable OfflinePlayer blockOwner, @Nullable BlockState blockState) {
        super(resolvedDamager);

        Preconditions.checkNotNull(projectile, "projectile");

        this.projectile = projectile;
        this.projectileDistance = projectileDistance;
        this.blockOwner = blockOwner;
        this.blockState = blockState;
    }

    public @Nonnull Projectile getProjectile() {
        return this.projectile;
    }

    public @Nullable double getDistance() {
        return this.projectileDistance;
    }

    public @Nullable OfflinePlayer getBlockOwner() {
        return this.blockOwner;
    }

    public @Nullable BlockState getBlockState() {
        return this.blockState;
    }

    private final @Nonnull Projectile projectile;
    private final @Nullable Double projectileDistance;
    private final @Nullable OfflinePlayer blockOwner;
    private final @Nullable BlockState blockState;

    @Override
    public @Nonnull String toString() {
        return "ProjectileDamageInfo{shooter=" + this.resolvedDamager + ",projectile=" + this.projectile + ",distance=" + this.projectileDistance + ",blockOwner=" + this.blockOwner + ",blockState=" + this.blockState + "}";
    }
}
