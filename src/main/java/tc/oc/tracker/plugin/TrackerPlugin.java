package tc.oc.tracker.plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tc.oc.tracker.ExplosiveTracker;
import tc.oc.tracker.Trackers;
import tc.oc.tracker.base.FakeExplosiveTracker;
import tc.oc.tracker.base.SimpleExplosiveTracker;

public class TrackerPlugin extends JavaPlugin {
    @Override
    public void onDisable() {
        ExplosiveTracker oldExplosiveTracker = Trackers.getExplosiveTracker();
        if(oldExplosiveTracker instanceof SimpleExplosiveTracker) {
            Trackers.setExplosiveTracker(new FakeExplosiveTracker());
        }
    }

    @Override
    public void onEnable() {
        ExplosiveTracker explosiveTracker = new SimpleExplosiveTracker();

        this.registerEvents(new ExplosiveListener(explosiveTracker));
        Trackers.setExplosiveTracker(explosiveTracker);
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
