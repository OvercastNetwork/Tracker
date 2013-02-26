package tc.oc.tracker.base;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.DamageInfo;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.DamageResolverManager;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.damage.BukkitDamageInfo;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class SimpleResolverManager implements DamageResolverManager {
    public boolean isRegistered(@Nonnull DamageResolver resolver) {
        Preconditions.checkNotNull(resolver, "resolver");

        return this.resolvers.contains(resolver);
    }

    public void register(@Nonnull DamageResolver resolver) {
        Preconditions.checkNotNull(resolver, "resolver");

        this.register(resolver, Collections.<Class<? extends DamageResolver>>emptySet());
    }

    public void register(@Nonnull DamageResolver resolver, @Nonnull Class<? extends DamageResolver>... before) {
        Preconditions.checkNotNull(resolver, "resolver");
        Preconditions.checkNotNull(before, "resolvers before");

        this.register(resolver, ImmutableSet.copyOf(before));
    }

    public void register(@Nonnull DamageResolver resolver, @Nonnull Collection<Class<? extends DamageResolver>> resolversBefore) {
        Preconditions.checkNotNull(resolver, "resolver");
        Preconditions.checkNotNull(resolversBefore, "resolvers before");
        Preconditions.checkArgument(!this.isRegistered(resolver), "resolver is already registered");

        ResolverEntry entry = new ResolverEntry(resolver, ImmutableSet.copyOf(resolversBefore));

        Set<ResolverEntry> before = this.getEntriesBefore(entry);
        Set<ResolverEntry> after = this.getEntriesAfter(entry);

        int index = 0;
        for(; index < this.resolvers.size(); index++) {
            ResolverEntry loopEntry = this.resolvers.get(index);

            before.remove(loopEntry);
            if(after.contains(loopEntry)) {
                if(before.isEmpty()) {
                    break;
                } else {
                    throw new IllegalArgumentException("failed to solve constraints (" + resolver + " could not come before " + loopEntry.resolver + ") while obeying all other constraints");
                }
            }
        }

        this.resolvers.add(index, entry);
    }

    private Set<ResolverEntry> getEntriesBefore(ResolverEntry entry) {
        Set<ResolverEntry> before = Sets.newHashSet();

        for(ResolverEntry loopEntry : this.resolvers) {
            if(loopEntry.isBefore(entry)) {
                before.add(loopEntry);
            }
        }

        return before;
    }

    private Set<ResolverEntry> getEntriesAfter(ResolverEntry entry) {
        Set<ResolverEntry> after = Sets.newHashSet();

        for(ResolverEntry loopEntry : this.resolvers) {
            if(loopEntry.isAfter(entry)) {
                after.add(loopEntry);
            }
        }

        return after;
    }

    public void unregister(@Nonnull DamageResolver resolver) {
        Preconditions.checkNotNull(resolver, "resolver");

        this.resolvers.remove(resolver);
    }

    public @Nonnull Set<DamageResolver> getResolvers() {
        ImmutableSet.Builder<DamageResolver> resolvers = ImmutableSet.builder();

        for(ResolverEntry entry : this.resolvers) {
            resolvers.add(entry.resolver);
        }

        return resolvers.build();
    }

    public @Nonnull DamageInfo resolve(@Nonnull LivingEntity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        DamageInfo info = null;

        for(ResolverEntry entry : this.resolvers) {
            DamageInfo resolvedInfo = entry.resolver.resolve(entity, lifetime, damageEvent);
            if(resolvedInfo != null) {
                info = resolvedInfo;
            }
        }

        if(info == null) {
            info = new BukkitDamageInfo(damageEvent.getCause());
        }

        return info;
    }

    private final @Nonnull List<ResolverEntry> resolvers = Lists.newArrayList();

    private static class ResolverEntry {
        public final DamageResolver resolver;
        public final Set<Class<? extends DamageResolver>> before;

        public ResolverEntry(DamageResolver resolver, Set<Class<? extends DamageResolver>> before) {
            this.resolver = resolver;
            this.before = before;
        }

        public boolean isBefore(ResolverEntry entry) {
            for(Class<? extends DamageResolver> cls : entry.before) {
                if(cls.isInstance(this.resolver)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isAfter(ResolverEntry entry) {
            for(Class<? extends DamageResolver> cls : this.before) {
                if(cls.isInstance(entry.resolver)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof DamageResolver) {
                return this.resolver.equals(obj);
            } else {
                return super.equals(obj);
            }
        }
    }
}
