package tc.oc.tracker.plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tc.oc.tracker.ExplosiveTracker;
import tc.oc.tracker.Trackers;
import tc.oc.tracker.base.SimpleExplosiveTracker;

public class TrackerPlugin extends JavaPlugin {
    @Override
    public void onDisable() {
        Trackers.getManager().clearTracker(ExplosiveTracker.class, SimpleExplosiveTracker.class);
    }

    @Override
    public void onEnable() {
        ExplosiveTracker explosiveTracker = new SimpleExplosiveTracker();

        this.registerEvents(new ExplosiveListener(explosiveTracker));
        Trackers.getManager().setRealTracker(ExplosiveTracker.class, explosiveTracker);
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
