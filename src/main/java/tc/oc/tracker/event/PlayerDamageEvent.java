package tc.oc.tracker.event;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.joda.time.Instant;
import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.Lifetime;

import javax.annotation.Nonnull;

public class PlayerDamageEvent extends EntityDamageEvent<Player> {
    public PlayerDamageEvent(@Nonnull Player player, @Nonnull Lifetime lifetime, int damage, @Nonnull Location location, @Nonnull Instant time, @Nonnull DamageInfo info) {
        super(player, lifetime, damage, location, time, info);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
