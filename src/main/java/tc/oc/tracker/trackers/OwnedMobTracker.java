package tc.oc.tracker.trackers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import tc.oc.tracker.Tracker;

public interface OwnedMobTracker extends Tracker {
    boolean hasOwner(@Nonnull LivingEntity entity);

    @Nullable OfflinePlayer getOwner(@Nonnull LivingEntity entity);

    @Nullable OfflinePlayer setOwner(@Nonnull LivingEntity entity, @Nullable Player player);
}
