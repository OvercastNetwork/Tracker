package tc.oc.tracker.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import tc.oc.tracker.event.PlayerOnGroundEvent;
import tc.oc.tracker.plugin.TrackerPlugin;
import tc.oc.tracker.util.EventUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnGroundTask implements Runnable {
    private final TrackerPlugin plugin;
    private final Map<UUID, Boolean> grounded;
    private int taskId;

    public OnGroundTask(TrackerPlugin plugin) {
        this.plugin = plugin;
        this.grounded = new HashMap<>();
    }

    public void start() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0, 3);
    }

    public void stop() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
    }

    @Override
    public void run() {
        Map<UUID, Boolean> clone = new HashMap<>(this.grounded);

        for (Player player : Bukkit.getOnlinePlayers()) {
            Boolean last = clone.get(player.getUniqueId());
            boolean current = player.isOnGround();

            if (last == null || last != current) {
                PlayerOnGroundEvent call = new PlayerOnGroundEvent(player, current);
                this.grounded.put(player.getUniqueId(), current);

                for (EventPriority priority : EventPriority.values())
                    EventUtil.callEvent(call, PlayerOnGroundEvent.getHandlerList(), priority);
            }
        }

        // Remove players who have logged out
        for (UUID uuid : clone.keySet()) {
            if (Bukkit.getPlayer(uuid) == null)
                this.grounded.remove(uuid);
        }
    }
}
