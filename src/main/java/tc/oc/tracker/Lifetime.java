package tc.oc.tracker;

import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.Instant;

public interface Lifetime {
    @Nullable Instant getStart();

    void setStart(@Nonnull Instant start);

    @Nullable Instant getEnd();

    void setEnd(@Nonnull Instant end);

    @Nonnull List<Damage> getDamage();

    @Nonnull ListIterator<Damage> getDamageFirst();

    @Nonnull ListIterator<Damage> getDamageLast();

    @Nullable Damage getFirstDamage();

    @Nullable Damage getLastDamage();

    void addDamage(@Nonnull Damage damage);
}
