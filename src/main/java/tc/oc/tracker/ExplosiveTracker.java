package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

public interface ExplosiveTracker extends Tracker {
    boolean hasOwner(@Nonnull TNTPrimed entity);

    @Nullable OfflinePlayer getOwner(@Nonnull TNTPrimed entity);

    @Nullable OfflinePlayer setOwner(@Nonnull TNTPrimed entity, @Nullable OfflinePlayer player);

    boolean hasPlacer(@Nonnull Block block);

    @Nullable OfflinePlayer getPlacer(@Nonnull Block block);

    @Nullable OfflinePlayer setPlacer(@Nonnull Block block, @Nullable OfflinePlayer player);
}
