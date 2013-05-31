package tc.oc.tracker.trackers.base;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import tc.oc.tracker.base.AbstractTracker;
import tc.oc.tracker.trackers.OwnedMobTracker;

public class SimpleOwnedMobTracker extends AbstractTracker implements OwnedMobTracker {
    private final Map<LivingEntity, OfflinePlayer> ownedMobs = Maps.newHashMap();

    public boolean hasOwner(@Nonnull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "entity");

        return this.ownedMobs.containsKey(entity);
    }

    public @Nullable OfflinePlayer getOwner(@Nonnull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "entity");

        return this.ownedMobs.get(entity);
    }

    public @Nonnull OfflinePlayer setOwner(@Nonnull LivingEntity entity, @Nonnull Player player) {
        Preconditions.checkNotNull(entity, "entity");
        Preconditions.checkNotNull(player, "player");

        return this.ownedMobs.put(entity, player);
    }

    public @Nonnull OfflinePlayer clearOwner(@Nonnull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "entity");

        return this.ownedMobs.remove(entity);
    }

    public void clear(@Nonnull World world) {
     // clear information about blocks in that world
        Iterator<Map.Entry<LivingEntity, OfflinePlayer>> mobIt = this.ownedMobs.entrySet().iterator();
        while(mobIt.hasNext()) {
            LivingEntity entity = mobIt.next().getKey();
            if(entity.getWorld().equals(world)) {
                entity.remove();
            }
        }
    }
}
