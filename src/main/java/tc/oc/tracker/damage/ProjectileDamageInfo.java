package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

import tc.oc.tracker.base.AbstractDamageInfo;

import com.google.common.base.Preconditions;

public class ProjectileDamageInfo extends AbstractDamageInfo {
    public ProjectileDamageInfo(@Nonnull Projectile projectile, @Nullable LivingEntity resolvedDamager, @Nullable Double projectileDistance) {
        super(resolvedDamager);

        Preconditions.checkNotNull(projectile, "projectile");

        this.projectile = projectile;
        this.projectileDistance = projectileDistance;
    }

    public @Nonnull Projectile getProjectile() {
        return this.projectile;
    }

    public @Nullable double getDistance() {
        return this.projectileDistance;
    }

    protected final @Nonnull Projectile projectile;
    protected final @Nullable Double projectileDistance;

    @Override
    public @Nonnull String toString() {
        return "ProjectileDamageInfo{shooter=" + this.resolvedDamager + ",projectile=" + this.projectile + ",distance=" + this.projectileDistance + "}";
    }
}
