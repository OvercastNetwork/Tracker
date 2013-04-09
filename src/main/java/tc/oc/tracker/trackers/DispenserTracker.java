package tc.oc.tracker.trackers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tc.oc.tracker.Tracker;

public interface DispenserTracker extends Tracker {
    boolean hasOwner(@Nonnull Entity entity);

    @Nullable BlockState getOwner(@Nonnull Entity entity);

    @Nullable BlockState setOwner(@Nonnull Entity entity, @Nullable BlockState block);

    boolean hasPlacer(@Nonnull Block block);

    @Nullable OfflinePlayer getPlacer(@Nonnull Block block);

    @Nullable OfflinePlayer setPlacer(@Nonnull Block block, @Nullable Player player);

    @Nonnull OfflinePlayer clearPlacer(@Nonnull Block block);
}
