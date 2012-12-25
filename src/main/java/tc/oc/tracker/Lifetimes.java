package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.SimpleLifetimeManager;

public final class Lifetimes {
    private Lifetimes() { }

    public static @Nonnull LifetimeManager getManager() {
        if(manager == null) {
            manager = new SimpleLifetimeManager();
        }
        return manager;
    }

    public static @Nonnull Lifetime getLifetime(@Nonnull LivingEntity entity) {
        return getManager().getLifetime(entity);
    }

    private static @Nullable LifetimeManager manager;
}
