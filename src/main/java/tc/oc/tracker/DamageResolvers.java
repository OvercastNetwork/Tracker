package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import tc.oc.tracker.base.SimpleResolverManager;

public final class DamageResolvers {
    private DamageResolvers() { }

    public static @Nonnull DamageResolverManager getManager() {
        if(manager == null) {
            manager = new SimpleResolverManager();
        }
        return manager;
    }

    private static @Nullable DamageResolverManager manager = null;
}
