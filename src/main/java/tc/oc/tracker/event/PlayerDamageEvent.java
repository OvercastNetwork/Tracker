package tc.oc.tracker.event;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.joda.time.Instant;
import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.Lifetime;

import javax.annotation.Nonnull;

public class PlayerDamageEvent extends EntityDamageEvent {
    private final Player player;

    public PlayerDamageEvent(@Nonnull Player player, @Nonnull Lifetime lifetime, int damage, @Nonnull Location location, @Nonnull Instant time, @Nonnull DamageInfo info) {
        super(player, lifetime, damage, location, time, info);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
