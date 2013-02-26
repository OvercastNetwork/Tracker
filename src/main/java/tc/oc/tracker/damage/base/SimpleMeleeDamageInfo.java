package tc.oc.tracker.damage.base;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.MeleeDamageInfo;

import com.google.common.base.Preconditions;

public class SimpleMeleeDamageInfo extends AbstractDamageInfo implements MeleeDamageInfo {
    public SimpleMeleeDamageInfo(@Nonnull LivingEntity attacker, @Nonnull Material weaponMaterial) {
        super(attacker);

        Preconditions.checkNotNull(attacker, "attacker");
        Preconditions.checkNotNull(weaponMaterial, "weapon material");

        this.weaponMaterial = weaponMaterial;
    }

    public @Nonnull LivingEntity getAttacker() {
        return this.resolvedDamager;
    }

    public @Nonnull Material getWeapon() {
        return this.weaponMaterial;
    }

    private final @Nonnull Material weaponMaterial;
}
