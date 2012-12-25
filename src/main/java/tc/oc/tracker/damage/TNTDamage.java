package tc.oc.tracker.damage;

import javax.annotation.Nonnull;

import org.bukkit.entity.TNTPrimed;

import tc.oc.tracker.Damage;
import tc.oc.tracker.base.AbstractDamage;
import tc.oc.tracker.base.AbstractDamageBuilder;

import com.google.common.base.Preconditions;

public class TNTDamage extends AbstractDamage {
    private TNTDamage(@Nonnull Damage base, @Nonnull TNTPrimed tnt) {
        super(base);

        Preconditions.checkNotNull(tnt, "tnt");

        this.tnt = tnt;
    }

    public TNTPrimed getTNT() {
        return this.tnt;
    }

    private final TNTPrimed tnt;

    public static class Builder extends AbstractDamageBuilder<Builder> {
        public @Nonnull Builder tnt(@Nonnull TNTPrimed tnt) {
            Preconditions.checkNotNull(tnt, "tnt");

            this.tnt = tnt;

            return this;
        }

        public @Nonnull TNTDamage build() throws IllegalStateException {
            Damage base = this.buildPartial();
            Preconditions.checkState(this.tnt != null, "tnt cannot be null");

            return new TNTDamage(base, this.tnt);
        }

        protected TNTPrimed tnt;

        @Override
        protected @Nonnull Builder me() {
            return this;
        }
    }
}
