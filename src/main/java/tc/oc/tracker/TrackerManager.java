package tc.oc.tracker;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.World;

public interface TrackerManager {
    Set<Tracker> getTrackers();

    boolean hasTracker(@Nonnull Class<? extends Tracker> trackerClass);

    @Nullable <T extends Tracker> T getTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T setTracker(@Nonnull Class<T> trackerClass, @Nullable T tracker);

    @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass, @Nonnull Class<? extends T> trackerImplClass);

    void clearTrackers(@Nonnull World world);
}
