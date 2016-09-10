package tc.oc.tracker.plugin;

import javax.annotation.Nullable;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tc.oc.tracker.DamageResolverManager;
import tc.oc.tracker.DamageResolvers;
import tc.oc.tracker.TrackerManager;
import tc.oc.tracker.Trackers;
import tc.oc.tracker.damage.resolvers.AnvilDamageResolver;
import tc.oc.tracker.damage.resolvers.BlockDamageResolver;
import tc.oc.tracker.damage.resolvers.DispensedProjectileDamageResolver;
import tc.oc.tracker.damage.resolvers.FallDamageResolver;
import tc.oc.tracker.damage.resolvers.GravityDamageResolver;
import tc.oc.tracker.damage.resolvers.LavaDamageResolver;
import tc.oc.tracker.damage.resolvers.MeleeDamageResolver;
import tc.oc.tracker.damage.resolvers.OwnedMobDamageResolver;
import tc.oc.tracker.damage.resolvers.ProjectileDamageResolver;
import tc.oc.tracker.damage.resolvers.TNTDamageResolver;
import tc.oc.tracker.damage.resolvers.VoidDamageResolver;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.trackers.AnvilTracker;
import tc.oc.tracker.trackers.DispenserTracker;
import tc.oc.tracker.trackers.ExplosiveTracker;
import tc.oc.tracker.trackers.OwnedMobTracker;
import tc.oc.tracker.trackers.ProjectileDistanceTracker;
import tc.oc.tracker.trackers.base.SimpleAnvilTracker;
import tc.oc.tracker.trackers.base.SimpleDispenserTracker;
import tc.oc.tracker.trackers.base.SimpleExplosiveTracker;
import tc.oc.tracker.trackers.base.SimpleOwnedMobTracker;
import tc.oc.tracker.trackers.base.SimpleProjectileDistanceTracker;
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
        this.registerEvents(new CustomEventListener(this));

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
        this.registerEvents(new GravityListener(this, gravityKillTracker));

        tm.setTracker(ExplosiveTracker.class, explosiveTracker);
        tm.setTracker(SimpleGravityKillTracker.class, gravityKillTracker);

        DispenserTracker dispenserTracker = new SimpleDispenserTracker();
        dispenserTracker.enable();

        this.registerEvents(new DispenserListener(dispenserTracker));
        tm.setTracker(DispenserTracker.class, dispenserTracker);

        ProjectileDistanceTracker projectileDistanceTracker = new SimpleProjectileDistanceTracker();
        projectileDistanceTracker.enable();

        this.registerEvents(new ProjectileDistanceListener(projectileDistanceTracker));
        tm.setTracker(ProjectileDistanceTracker.class, projectileDistanceTracker);

        OwnedMobTracker ownedMobTracker = new SimpleOwnedMobTracker();
        ownedMobTracker.enable();

        this.registerEvents(new OwnedMobListener(ownedMobTracker));
        tm.setTracker(OwnedMobTracker.class, ownedMobTracker);

        AnvilTracker anvilTracker = new SimpleAnvilTracker();
        anvilTracker.enable();

        this.registerEvents(new AnvilListener(anvilTracker));
        tm.setTracker(AnvilTracker.class, anvilTracker);

        // register damage resolvers
        DamageResolverManager drm = DamageResolvers.getManager();

        drm.register(new BlockDamageResolver());
        drm.register(new FallDamageResolver());
        drm.register(new LavaDamageResolver());
        drm.register(new MeleeDamageResolver());
        drm.register(new ProjectileDamageResolver(projectileDistanceTracker));
        drm.register(new TNTDamageResolver(explosiveTracker, dispenserTracker));
        drm.register(new VoidDamageResolver());
        drm.register(new GravityDamageResolver(gravityKillTracker));
        drm.register(new DispensedProjectileDamageResolver(projectileDistanceTracker, dispenserTracker));
        drm.register(new OwnedMobDamageResolver(ownedMobTracker));
        drm.register(new AnvilDamageResolver(anvilTracker));

        // debug
        // this.registerEvents(new DebugListener());
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
