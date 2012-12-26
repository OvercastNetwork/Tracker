package tc.oc.tracker;

import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public interface ResolverManager {
    boolean isRegistered(@Nonnull DamageResolver resolver);

    void register(@Nonnull DamageResolver resolver);

    void register(@Nonnull DamageResolver resolver, @Nonnull Class<? extends DamageResolver>... before);

    void register(@Nonnull DamageResolver resolver, @Nonnull Set<Class<? extends DamageResolver>> before);

    void unregister(@Nonnull DamageResolver resolver);

    @Nonnull Set<DamageResolver> getResolvers();

    @Nonnull Damage resolve(@Nonnull Entity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent);
}
