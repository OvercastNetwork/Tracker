package tc.oc.tracker.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import tc.oc.tracker.event.EntityDamageEvent;

public class DebugListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageEvent event) {
        Bukkit.broadcastMessage(event.getEntity().toString() + " damaged for " + event.getHearts() + " half hearts at " + event.getLocation() + " info: " + event.getInfo() + " cancelled?" + (event.isCancelled() ? "yes" : "no"));
    }
}
