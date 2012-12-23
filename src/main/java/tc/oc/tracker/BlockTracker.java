package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

public interface BlockTracker {
    @Nullable OfflinePlayer getPlacer(@Nonnull Block block);

    void setPlacer(@Nonnull Block block, @Nonnull OfflinePlayer player);
}
