package tc.oc.tracker.plugin;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.joda.time.Instant;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolvers;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.Lifetimes;
import tc.oc.tracker.event.EntityDamageEvent;
import tc.oc.tracker.event.PlayerDamageEvent;
import tc.oc.tracker.util.EventUtil;

import com.google.common.base.Preconditions;

public class EntityDamageEventListener implements Listener {
    public void register(@Nonnull Plugin plugin) {
        Preconditions.checkNotNull(plugin, "plugin");

        PluginManager pm = plugin.getServer().getPluginManager();

        for(EventPriority priority : EventPriority.values()) {
            pm.registerEvent(org.bukkit.event.entity.EntityDamageEvent.class, this, priority, new EntityDamageEventRunner(this, priority), plugin, false);
        }
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    public static class EntityDamageEventRunner implements EventExecutor {
        public EntityDamageEventRunner(@Nonnull EntityDamageEventListener parent, @Nonnull EventPriority priority) {
            Preconditions.checkNotNull(parent, "parent");
            Preconditions.checkNotNull(priority, "event priority");

            this.parent = parent;
            this.priority = priority;
        }

        public void execute(Listener listener, Event event) throws EventException {
            if(listener != this.parent) return;

            if(!(event instanceof org.bukkit.event.entity.EntityDamageEvent)) return;
            org.bukkit.event.entity.EntityDamageEvent bukkit = (org.bukkit.event.entity.EntityDamageEvent) event;

            if(!(bukkit.getEntity() instanceof LivingEntity)) return;
            LivingEntity entity = (LivingEntity) bukkit.getEntity();

            if(entity.isDead()) return;

            DamageAPIHelper helper = DamageAPIHelper.get();
            Lifetime lifetime = Lifetimes.getLifetime(entity);

            // get our version of the event
            EntityDamageEvent our = DamageAPIHelper.get().getOurEvent(bukkit);
            if(our == null) {
                int hearts = (int) bukkit.getDamage();
                Location location = entity.getLocation();
                Instant time = Instant.now();
                DamageInfo info = DamageResolvers.getManager().resolve(entity, lifetime, bukkit);

                if (entity instanceof Player)
                    our = new PlayerDamageEvent((Player) entity, lifetime, hearts, location, time, info);
                else
                    our = new EntityDamageEvent(entity, lifetime, hearts, location, time, info);
                helper.setOurEvent(bukkit, our);
            }

            // update mutable information
            our.setCancelled(bukkit.isCancelled());
            our.setDamage((int) bukkit.getDamage());

            // call
            EventUtil.callEvent(our, EntityDamageEvent.getHandlerList(), this.priority);

            // update bukkit event
            bukkit.setCancelled(our.isCancelled());
            bukkit.setDamage(our.getDamage());

            // clean up
            if(this.priority == EventPriority.MONITOR) {
                DamageAPIHelper.get().setOurEvent(bukkit, null);

                if(!bukkit.isCancelled()) {
                    lifetime.addDamage(our.toDamageObject());
                }
            }
        }

        private final @Nonnull EntityDamageEventListener parent;
        private final @Nonnull EventPriority priority;
    }
}
