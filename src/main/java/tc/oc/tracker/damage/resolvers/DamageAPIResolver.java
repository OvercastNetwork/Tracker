package tc.oc.tracker.damage.resolvers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.*;

/**
 * Resolves the damage stored in the {@link DamageAPI}.
 *
 * When a plugin uses the API to inflict damage on an entity, it specifies its
 * own {@link Damage} object to use. However, plugins listening on the Bukkit
 * event will use the regular channels to fetch the object for the event.
 * Therefore, this resolver is necessary to feed the proper object to those
 * event listeners.
 */
public class DamageAPIResolver implements DamageResolver {
    /** @see DamageResolvers#resolve */
    public @Nullable DamageInfo resolve(@Nonnull Entity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        return DamageAPI.getAPIDamage(damageEvent);
    }

    static {
        DamageResolvers.getManager().register(new DamageAPIResolver());
    }
}
