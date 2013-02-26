package tc.oc.tracker.plugin;

import javax.annotation.Nullable;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tc.oc.tracker.DamageResolverManager;
import tc.oc.tracker.DamageResolvers;
import tc.oc.tracker.TrackerManager;
import tc.oc.tracker.Trackers;
import tc.oc.tracker.damage.resolvers.*;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.trackers.ExplosiveTracker;
import tc.oc.tracker.trackers.base.SimpleExplosiveTracker;
import tc.oc.tracker.trackers.base.gravity.SimpleGravityKillTracker;

public class TrackerPlugin extends JavaPlugin {
    public @Nullable TickTimer tickTimer;

    @Override
    public void onDisable() {
        Trackers.getManager().clearTracker(ExplosiveTracker.class, SimpleExplosiveTracker.class);
    }

    @Override
    public void onEnable() {
        // basic operation listeners
        this.registerEvents(new LifetimeListener());
        this.registerEvents(new WorldListener(Trackers.getManager()));

        EntityDamageEventListener damageEventListener = new EntityDamageEventListener();
        damageEventListener.register(this);

        // initialize timer
        this.tickTimer = new TickTimer(this);
        this.tickTimer.start();

        // tracker setup
        TrackerManager tm = Trackers.getManager();

        ExplosiveTracker explosiveTracker = new SimpleExplosiveTracker();
        SimpleGravityKillTracker gravityKillTracker = new SimpleGravityKillTracker(this, this.tickTimer);

        explosiveTracker.enable();
        gravityKillTracker.enable();

        this.registerEvents(new ExplosiveListener(explosiveTracker));
        this.registerEvents(new GravityListener(this, gravityKillTracker, this.tickTimer));

        tm.setTracker(ExplosiveTracker.class, explosiveTracker);
        tm.setTracker(SimpleGravityKillTracker.class, gravityKillTracker);

        // register damage resolvers
        DamageResolverManager drm = DamageResolvers.getManager();

        drm.register(new BlockDamageResolver());
        drm.register(new FallDamageResolver());
        drm.register(new LavaDamageResolver());
        drm.register(new MeleeDamageResolver());
        drm.register(new ProjectileDamageResolver());
        drm.register(new TNTDamageResolver(explosiveTracker));
        drm.register(new VoidDamageResolver());
        drm.register(new GravityDamageResolver(gravityKillTracker, this.tickTimer));

        // debug
        // this.registerEvents(new DebugListener());
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
