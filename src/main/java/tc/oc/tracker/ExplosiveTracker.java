package tc.oc.tracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.TNTPrimed;

public interface ExplosiveTracker extends Tracker {
    @Nullable OfflinePlayer getPlacer(@Nonnull TNTPrimed entity);

    @Nullable OfflinePlayer setPlacer(@Nonnull TNTPrimed entity, @Nonnull OfflinePlayer player);
}
