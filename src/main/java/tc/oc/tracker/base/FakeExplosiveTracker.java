package tc.oc.tracker.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.ExplosiveTracker;
import tc.oc.tracker.Trackers;

public class FakeExplosiveTracker extends FakeTracker implements ExplosiveTracker {
    static {
        Trackers.getManager().setFakeTracker(ExplosiveTracker.class, new FakeExplosiveTracker());
    }

    public boolean hasOwner(@Nonnull TNTPrimed entity) {
        return false;
    }

    public @Nullable Player getOwner(@Nonnull TNTPrimed entity) {
        return null;
    }

    public @Nullable Player setOwner(@Nonnull TNTPrimed entity, @Nullable Player player) {
        return null;
    }

    public boolean hasPlacer(@Nonnull Block block) {
        return false;
    }

    public @Nullable Player getPlacer(@Nonnull Block block) {
        return null;
    }

    public @Nullable Player setPlacer(@Nonnull Block block, @Nullable Player player) {
        return null;
    }
}
