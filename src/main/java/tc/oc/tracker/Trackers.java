package tc.oc.tracker;

import javax.annotation.Nonnull;

import tc.oc.tracker.base.SimpleTrackerManager;


public final class Trackers {
    private Trackers() { }

    public static @Nonnull TrackerManager getManager() {
        if(manager == null) {
            manager = new SimpleTrackerManager();
        }
        return manager;
    }

    /**
     * Utility method for {@link TrackerManager#getTracker}
     */
    public static <T extends Tracker> T getTracker(@Nonnull Class<T> trackerClass) {
        return getManager().getTracker(trackerClass);
    }

    private static TrackerManager manager;
}
