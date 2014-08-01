package tc.oc.tracker.damage.resolvers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.base.SimplePotionDamageInfo;

import javax.annotation.Nonnull;

public class PotionDamageResolver implements DamageResolver {
    public DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if (!(damageEvent.getEntity() instanceof Player)) return null;
        if (damageEvent.getCause() == EntityDamageEvent.DamageCause.WITHER || damageEvent.getCause() == EntityDamageEvent.DamageCause.POISON) {
            Player player = (Player) damageEvent.getEntity();

            if (damageEvent.getCause() == EntityDamageEvent.DamageCause.POISON) {
                return new SimplePotionDamageInfo(null, getPotionEffect(player, PotionEffectType.POISON));
            } else if (damageEvent.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                return new SimplePotionDamageInfo(null, getPotionEffect(player, PotionEffectType.WITHER));
            }
        }
        if (damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent sub = (EntityDamageByEntityEvent) damageEvent;
            if (sub.getDamager() instanceof ThrownPotion) {
                ThrownPotion thrownPotion = (ThrownPotion) sub.getDamager();
                PotionEffect potionEffect = null;
                for (PotionEffect pe : thrownPotion.getEffects()) {
                    if (pe.getType().equals(PotionEffectType.HARM)) {
                        potionEffect = pe;
                        break;
                    }
                }
                if (potionEffect != null) {
                    if (thrownPotion.getShooter() != null) {
                        return new SimplePotionDamageInfo(thrownPotion.getShooter(), potionEffect);
                    }
                    return new SimplePotionDamageInfo(null, potionEffect);
                }
            }
        }
        return null;
    }

    private PotionEffect getPotionEffect(Player player, PotionEffectType potionEffectType) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().equals(potionEffectType)) {
                return potionEffect;
            }
        }
        return null;
    }
}
