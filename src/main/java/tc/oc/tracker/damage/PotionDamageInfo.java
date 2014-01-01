package tc.oc.tracker.damage;


import org.bukkit.potion.PotionEffectType;
import tc.oc.tracker.DamageInfo;

/**
 * Represents the damage caused by potions
 */
public interface PotionDamageInfo extends DamageInfo {
    /**
     * Returns the PotionEffectType of the PotionEffect
     *
     * @return type of potion effect
     */
    PotionEffectType getType();

    /**
     * Returns the amplifier of the potion effect
     *
     * @return potion effect amplifier
     */
    int getAmplifier();

    /**
     * Returns the duration of the potion effect
     *
     * @return duration of the potion effect
     */
    int getDuration();
}
