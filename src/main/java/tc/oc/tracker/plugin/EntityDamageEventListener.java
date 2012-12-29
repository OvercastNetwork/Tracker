package tc.oc.tracker.plugin;

import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
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

    public @Nonnull EntityDamageEvent getOurEvent(@Nonnull org.bukkit.event.entity.EntityDamageEvent bukkit) {
        Preconditions.checkNotNull(bukkit, "entity damage event");
        Preconditions.checkArgument(bukkit.getEntity() instanceof LivingEntity, "damage event must have living entity");

        EntityDamageEvent event = this.events.get(bukkit);
        if(event == null) {
            LivingEntity entity = (LivingEntity) bukkit.getEntity();
            Lifetime lifetime = Lifetimes.getLifetime(entity);
            int hearts = bukkit.getDamage();
            Location location = entity.getLocation();
            Instant time = Instant.now();
            DamageInfo info = DamageResolvers.getManager().resolve(entity, lifetime, bukkit);

            event = new EntityDamageEvent(entity, lifetime, hearts, location, time, info);
            this.events.put(bukkit, event);
        }

        return event;
    }

    private final Map<org.bukkit.event.entity.EntityDamageEvent, EntityDamageEvent> events = new WeakHashMap<org.bukkit.event.entity.EntityDamageEvent, EntityDamageEvent>();

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

            // get our version of the event
            EntityDamageEvent our = this.parent.getOurEvent(bukkit);

            // update mutable information
            our.setCancelled(bukkit.isCancelled());
            our.setHearts(bukkit.getDamage());

            // call
            EventUtil.callEvent(our, EntityDamageEvent.getHandlerList(), this.priority);

            // update bukkit event
            bukkit.setCancelled(our.isCancelled());
            bukkit.setDamage(our.getHearts());
        }

        private final @Nonnull EntityDamageEventListener parent;
        private final @Nonnull EventPriority priority;
    }
}
