package tc.oc.tracker.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.ExplosiveTracker;

public class FakeExplosiveTracker extends FakeTracker implements ExplosiveTracker {
    public boolean hasOwner(@Nonnull TNTPrimed entity) {
        return false;
    }

    public @Nullable OfflinePlayer getOwner(@Nonnull TNTPrimed entity) {
        return null;
    }

    public @Nullable OfflinePlayer setOwner(@Nonnull TNTPrimed entity, @Nullable OfflinePlayer player) {
        return null;
    }

    public boolean hasPlacer(@Nonnull Block block) {
        return false;
    }

    public @Nullable OfflinePlayer getPlacer(@Nonnull Block block) {
        return null;
    }

    public @Nullable OfflinePlayer setPlacer(@Nonnull Block block, @Nullable OfflinePlayer player) {
        return null;
    }
}
