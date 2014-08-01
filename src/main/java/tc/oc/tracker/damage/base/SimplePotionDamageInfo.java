package tc.oc.tracker.damage.base;

import com.google.common.base.Preconditions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tc.oc.tracker.base.AbstractDamageInfo;
import tc.oc.tracker.damage.PotionDamageInfo;

import javax.annotation.Nullable;

public class SimplePotionDamageInfo extends AbstractDamageInfo implements PotionDamageInfo {
    public SimplePotionDamageInfo(@Nullable LivingEntity resolvedDamager, PotionEffect potionEffect) {
        super(resolvedDamager);

        Preconditions.checkNotNull(potionEffect, "potion effect can not be null");

        this.potionEffectType = potionEffect.getType();
        this.amplifier = potionEffect.getAmplifier();
        this.duration = potionEffect.getDuration();
    }

    public PotionEffectType getType() {
        return this.potionEffectType;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public int getDuration() {
        return this.duration;
    }

    private PotionEffectType potionEffectType;
    private int amplifier;
    private int duration;
}
