package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.base.SimpleMeleeDamageInfo;

public class MeleeDamageResolver implements DamageResolver {
    public @Nullable DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        if(damageEvent instanceof EntityDamageByEntityEvent && damageEvent.getCause() == DamageCause.ENTITY_ATTACK) {
            EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) damageEvent;

            if(entityEvent.getDamager() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) entityEvent.getDamager();

                Material weaponMaterial;
                ItemStack held = entity.getEquipment().getItemInHand();
                if(held != null) {
                    weaponMaterial = held.getType();
                } else {
                    weaponMaterial = Material.AIR;
                }

                return new SimpleMeleeDamageInfo(attacker, weaponMaterial);
            }
        }

        return null;
    }
}
