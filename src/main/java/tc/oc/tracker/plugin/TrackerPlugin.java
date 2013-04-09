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
import tc.oc.tracker.trackers.DispenserTracker;
import tc.oc.tracker.trackers.ExplosiveTracker;
import tc.oc.tracker.trackers.ProjectileDistanceTracker;
import tc.oc.tracker.trackers.base.SimpleDispenserTracker;
import tc.oc.tracker.trackers.base.SimpleExplosiveTracker;
import tc.oc.tracker.trackers.base.SimpleProjectileDistanceTracker;

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
        explosiveTracker.enable();

        this.registerEvents(new ExplosiveListener(explosiveTracker));
        tm.setTracker(ExplosiveTracker.class, explosiveTracker);

        DispenserTracker dispenserTracker = new SimpleDispenserTracker();
        dispenserTracker.enable();

        this.registerEvents(new DispenserListener(dispenserTracker));
        tm.setTracker(DispenserTracker.class, dispenserTracker);

        ProjectileDistanceTracker projectileDistanceTracker = new SimpleProjectileDistanceTracker();
        projectileDistanceTracker.enable();

        this.registerEvents(new ProjectileDistanceListener(projectileDistanceTracker));
        tm.setTracker(ProjectileDistanceTracker.class, projectileDistanceTracker);

        // register damage resolvers
        DamageResolverManager drm = DamageResolvers.getManager();

        drm.register(new BlockDamageResolver());
        drm.register(new FallDamageResolver());
        drm.register(new LavaDamageResolver());
        drm.register(new MeleeDamageResolver());
        drm.register(new ProjectileDamageResolver(projectileDistanceTracker, dispenserTracker));
        drm.register(new TNTDamageResolver(explosiveTracker, dispenserTracker));
        drm.register(new VoidDamageResolver());

        // debug
        this.registerEvents(new DebugListener());
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
