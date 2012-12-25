package tc.oc.tracker;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;

public interface LifetimeManager {
    @Nonnull Lifetime getLifetime(@Nonnull LivingEntity entity);

    @Nonnull Lifetime setLifetime(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime);

    @Nonnull Lifetime newLifetime(@Nonnull LivingEntity entity);

    @Nonnull Lifetime endLifetime(@Nonnull LivingEntity entity);
}
