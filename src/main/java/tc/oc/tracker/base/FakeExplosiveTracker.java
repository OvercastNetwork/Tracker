package tc.oc.tracker.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.ExplosiveTracker;

public class FakeExplosiveTracker extends FakeTracker implements ExplosiveTracker {
    public @Nullable OfflinePlayer getPlacer(@Nonnull TNTPrimed entity) {
        // do nothing
        return null;
    }

    public @Nullable OfflinePlayer setPlacer(@Nonnull TNTPrimed entity, @Nonnull OfflinePlayer player) {
        // do nothing
        return null;
    }
}
