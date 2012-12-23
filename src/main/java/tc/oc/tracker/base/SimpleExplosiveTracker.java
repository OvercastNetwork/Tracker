package tc.oc.tracker.base;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.ExplosiveTracker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class SimpleExplosiveTracker extends AbstractTracker implements ExplosiveTracker {
    private final Map<TNTPrimed, OfflinePlayer> ownedTNTs = Maps.newHashMap();

    public @Nullable OfflinePlayer getPlacer(@Nonnull TNTPrimed entity) {
        Preconditions.checkNotNull(entity, "tnt entity");

        return this.ownedTNTs.get(entity);
    }

    public @Nullable OfflinePlayer setPlacer(@Nonnull TNTPrimed entity, @Nonnull OfflinePlayer player) {
        Preconditions.checkNotNull(entity, "tnt entity");
        Preconditions.checkNotNull(player, "player");

        return this.ownedTNTs.put(entity, player);
    }

    @Override
    protected void onEnable(World world) {
        // do nothing
    }

    @Override
    protected void onDisable(World world) {
        // clear information about entities in that world
        for(Iterator<Map.Entry<TNTPrimed, OfflinePlayer>> it = this.ownedTNTs.entrySet().iterator(); it.hasNext(); ) {
            TNTPrimed tnt = it.next().getKey();
            if(tnt.getWorld().equals(world)) {
                it.remove();
            }
        }
    }
}
