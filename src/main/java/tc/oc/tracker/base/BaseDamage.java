package tc.oc.tracker.base;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.joda.time.Instant;

import tc.oc.tracker.Damage;

public class BaseDamage extends AbstractDamage {
    public BaseDamage(Damage damage) {
        super(damage);
    }

    public BaseDamage(int hearts, Location location, Instant time, LivingEntity resolvedDamager) {
        super(hearts, location, time, resolvedDamager);
    }

    public static class Builder extends AbstractDamageBuilder<Builder> {
        public BaseDamage build() {
            return new BaseDamage(this.buildPartial());
        }

        @Override
        protected @Nonnull Builder me() {
            return this;
        }
    }
}
