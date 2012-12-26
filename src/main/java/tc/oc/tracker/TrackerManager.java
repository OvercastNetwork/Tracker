package tc.oc.tracker;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TrackerManager {
    Set<Tracker> getTrackers();

    boolean hasTracker(@Nonnull Class<? extends Tracker> trackerClass);

    boolean hasRealTracker(@Nonnull Class<? extends Tracker> trackerClass);

    boolean hasFakeTracker(@Nonnull Class<? extends Tracker> trackerClass);

    @Nullable <T extends Tracker> T getTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T getRealTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T getFakeTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T setRealTracker(@Nonnull Class<T> trackerClass, @Nullable T tracker);

    @Nullable <T extends Tracker> T setFakeTracker(@Nonnull Class<T> trackerClass, @Nullable T fakeTracker);

    @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass);

    @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass, @Nonnull Class<? extends T> trackerImplClass);
}
