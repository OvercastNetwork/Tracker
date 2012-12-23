package tc.oc.tracker.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import tc.oc.tracker.ExplosiveTracker;
import tc.oc.tracker.Trackers;
import tc.oc.tracker.base.FakeExplosiveTracker;
import tc.oc.tracker.base.SimpleExplosiveTracker;

public class TrackerPlugin extends JavaPlugin {
    @Override
    public void onDisable() {
        Trackers.setExplosiveTracker(new FakeExplosiveTracker());
    }

    @Override
    public void onEnable() {
        ExplosiveTracker explosiveTracker = new SimpleExplosiveTracker();
        this.getServer().getPluginManager().registerEvents(new ExplosiveListener(explosiveTracker), this);
        Trackers.setExplosiveTracker(explosiveTracker);


    }
}
