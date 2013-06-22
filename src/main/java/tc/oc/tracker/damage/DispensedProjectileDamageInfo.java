package tc.oc.tracker.damage;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

public class DispensedProjectileDamageInfo extends ProjectileDamageInfo {

    public DispensedProjectileDamageInfo(Projectile projectile, LivingEntity resolvedDamager, Double projectileDistance) {
        super(projectile, resolvedDamager, projectileDistance);
    }

    @Override
    public @Nonnull String toString() {
        return "DispensedProjectileDamageInfo{shooter=" + this.resolvedDamager + ",projectile=" + this.projectile + ",distance=" + this.projectileDistance + "}";
    }
}
