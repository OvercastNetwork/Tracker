package tc.oc.tracker.base;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.Instant;

import tc.oc.tracker.Damage;
import tc.oc.tracker.Lifetime;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class PlayerLifetime implements Lifetime {
    private Instant start;
    private Instant end;
    private final List<Damage> damage = Lists.newArrayList();

    public @Nullable Instant getStart() {
        return this.start;
    }

    public void setStart(@Nonnull Instant start) {
        Preconditions.checkNotNull(start, "start");

        this.start = start;
    }

    public @Nullable Instant getEnd() {
        return this.end;
    }

    public void setEnd(@Nonnull Instant end) {
        Preconditions.checkNotNull(end, "end");

        this.end = end;
    }

    public @Nonnull List<Damage> getDamage() {
        return Collections.unmodifiableList(this.damage);
    }

    public @Nonnull ListIterator<Damage> getDamageFirst() {
        return Collections.unmodifiableList(this.damage).listIterator();
    }

    public @Nonnull ListIterator<Damage> getDamageLast() {
        return Collections.unmodifiableList(this.damage).listIterator(this.damage.size() - 1);
    }

    public @Nullable Damage getFirstDamage() {
        if(!this.damage.isEmpty()) {
            return this.damage.get(0);
        } else {
            return null;
        }
    }

    public @Nullable Damage getLastDamage() {
        if(!this.damage.isEmpty()) {
            return this.damage.get(this.damage.size() - 1);
        } else {
            return null;
        }
    }

    public void addDamage(@Nonnull Damage damage) {
        Preconditions.checkNotNull(damage, "damage");

        this.damage.add(damage);
    }
}
