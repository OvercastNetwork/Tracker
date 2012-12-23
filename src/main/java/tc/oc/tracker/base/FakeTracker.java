package tc.oc.tracker.base;

import javax.annotation.Nonnull;

import org.bukkit.World;

import tc.oc.tracker.Tracker;

/**
 * Fake implementation for {@link Tracker}.
 */
public abstract class FakeTracker implements Tracker {
    public boolean isEnabled(@Nonnull World world) {
        return false;
    }

    public boolean enable() {
        return false;
    }

    public boolean enable(@Nonnull World world) {
        return false;
    }

    public boolean disable() {
        return false;
    }

    public boolean disable(@Nonnull World world) {
        return false;
    }
}
