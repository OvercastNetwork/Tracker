package tc.oc.tracker.damage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;

import tc.oc.tracker.base.AbstractDamageInfo;

public class OwnedMobDamageInfo extends AbstractDamageInfo {
    public OwnedMobDamageInfo(@Nullable LivingEntity resolvedDamager, @Nullable OfflinePlayer mobOwner) {
        super(resolvedDamager);

        this.mobOwner = mobOwner;
    }

    public @Nullable OfflinePlayer getMobOwner() {
        return this.mobOwner;
    }

    protected final @Nullable OfflinePlayer mobOwner;

    @Override
    public @Nonnull String toString() {
        return "OwnedMobDamageInfo{damager=" + this.resolvedDamager + ",mobOwner=" + this.mobOwner + "}";
    }
}
