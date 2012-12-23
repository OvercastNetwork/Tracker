package tc.oc.tracker.base;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import tc.oc.tracker.Tracker;
import tc.oc.tracker.TrackerManager;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class SimpleTrackerManager implements TrackerManager {
    public boolean hasTracker(@Nonnull Class<? extends Tracker> trackerClass) {
        return this.getTracker(trackerClass) != null;
    }

    public boolean hasRealTracker(@Nonnull Class<? extends Tracker> trackerClass) {
        return this.getRealTracker(trackerClass) != null;
    }

    public boolean hasFakeTracker(@Nonnull Class<? extends Tracker> trackerClass) {
        return this.getFakeTracker(trackerClass) != null;
    }

    public @Nullable <T extends Tracker> T getTracker(@Nonnull Class<T> trackerClass) {
        Preconditions.checkNotNull(trackerClass, "tracker class");

        T tracker = this.getRealTracker(trackerClass);
        if(tracker != null) {
            return tracker;
        } else {
            return this.getFakeTracker(trackerClass);
        }
    }

    public @Nullable <T extends Tracker> T getRealTracker(@Nonnull Class<T> trackerClass) {
        Preconditions.checkNotNull(trackerClass, "tracker class");

        return findTracker(this.trackers, trackerClass);
    }

    public @Nullable <T extends Tracker> T getFakeTracker(@Nonnull Class<T> trackerClass) {
        Preconditions.checkNotNull(trackerClass, "tracker class");

        return findTracker(this.fakeTrackers, trackerClass);
    }

    public @Nullable <T extends Tracker> T setRealTracker(@Nonnull Class<T> trackerClass, @Nullable T tracker) {
        Preconditions.checkNotNull(trackerClass, "tracker class");
        Preconditions.checkArgument(trackerClass.isInstance(tracker), "tracker is not an instance of the specified class");

        return setTrackerInDB(this.trackers, trackerClass, tracker);
    }

    public @Nullable <T extends Tracker> T setFakeTracker(@Nonnull Class<T> trackerClass, @Nullable T fakeTracker) {
        Preconditions.checkNotNull(trackerClass, "tracker class");
        Preconditions.checkArgument(trackerClass.isInstance(fakeTracker), "fake tracker is not an instance of the specified class");

        return setTrackerInDB(this.fakeTrackers, trackerClass, fakeTracker);
    }

    @SuppressWarnings("unchecked")
    public @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass) {
        Preconditions.checkNotNull(trackerClass, "tracker class");

        return (T) this.trackers.remove(trackerClass);
    }

    @SuppressWarnings("unchecked")
    public @Nullable <T extends Tracker> T clearTracker(@Nonnull Class<T> trackerClass, @Nonnull Class<? extends T> trackerImplClass) {
        Preconditions.checkNotNull(trackerClass, "tracker class");
        Preconditions.checkNotNull(trackerImplClass, "tracker implementation class");

        Tracker tracker = this.trackers.get(trackerClass);
        if(trackerImplClass.isInstance(tracker)) {
            return (T) this.trackers.remove(trackerClass);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static @Nullable <T extends Tracker> T findTracker(@Nonnull Map<Class<? extends Tracker>, Tracker> db, @Nonnull Class<T> search) {
        for(Map.Entry<Class<? extends Tracker>, Tracker> entry : db.entrySet()) {
            if(search.isAssignableFrom(entry.getKey())) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static @Nullable <T extends Tracker> T setTrackerInDB(@Nonnull Map<Class<? extends Tracker>, Tracker> db, @Nonnull Class<T> trackerClass, @Nullable T tracker) {
        if(tracker != null) {
            return (T) db.remove(trackerClass);
        } else {
            return (T) db.put(trackerClass, tracker);
        }
    }

    private final Map<Class<? extends Tracker>, Tracker> fakeTrackers = Maps.newHashMap();
    private final Map<Class<? extends Tracker>, Tracker> trackers = Maps.newHashMap();
}
