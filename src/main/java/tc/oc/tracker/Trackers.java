package tc.oc.tracker;

import javax.annotation.Nonnull;

import tc.oc.tracker.base.FakeExplosiveTracker;

import com.google.common.base.Preconditions;


public final class Trackers {
    private Trackers() { }

    private static @Nonnull ExplosiveTracker explosiveTracker = new FakeExplosiveTracker();

    public static @Nonnull ExplosiveTracker getExplosiveTracker() {
        return explosiveTracker;
    }

    public static void setExplosiveTracker(@Nonnull ExplosiveTracker newExplosiveTracker) {
        Preconditions.checkNotNull(newExplosiveTracker, "new explosive tracker");

        explosiveTracker = newExplosiveTracker;
    }
}
