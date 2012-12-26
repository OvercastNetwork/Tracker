package tc.oc.tracker.base;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.Damage;
import tc.oc.tracker.DamageResolver;
import tc.oc.tracker.Lifetime;
import tc.oc.tracker.ResolverManager;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class SimpleResolverManager implements ResolverManager {
    public boolean isRegistered(@Nonnull DamageResolver resolver) {
        Preconditions.checkNotNull(resolver, "resolver");

        return this.getResolvers().contains(resolver);
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

    public void register(@Nonnull DamageResolver resolver, @Nonnull Set<Class<? extends DamageResolver>> before) {
        Preconditions.checkNotNull(resolver, "resolver");
        Preconditions.checkNotNull(before, "resolvers before");
        Preconditions.checkArgument(!this.isRegistered(resolver), "resolver is already registered");

        this.entries.add(new ResolverEntry(resolver, ImmutableSet.copyOf(before)));
        this.invalidate();
    }

    public void unregister(@Nonnull DamageResolver resolver) {
        Preconditions.checkNotNull(resolver, "resolver");

        for(ResolverEntry entry : this.entries) {
            if(entry.resolver.equals(resolver)) {
                this.entries.remove(entry);
                this.invalidate();
                return;
            }
        }
    }

    public @Nonnull Set<DamageResolver> getResolvers() {
        if(this.resolvers == null) {
            this.bake();
        }
        return Collections.unmodifiableSet(this.resolvers);
    }

    public @Nonnull Damage resolve(@Nonnull Entity entity, @Nonnull Lifetime lifetime, @Nonnull EntityDamageEvent damageEvent) {
        Damage damage = null;

        for(DamageResolver resolver : this.getResolvers()) {
            Damage resolvedDamage = resolver.resolve(entity, lifetime, damageEvent);
            if(resolvedDamage != null) {
                damage = resolvedDamage;
            }
        }

        if(damage == null) {
            damage = new BaseDamage.Builder().base(damageEvent).build();
        }

        return damage;
    }

    private void invalidate() {
        this.resolvers = null;
    }

    private void bake() {
        Set<DamageResolver> resolvers = Sets.newLinkedHashSet();

        for(ResolverEntry entry : this.entries) {
            this.handle(entry, resolvers, Sets.<Class<? extends DamageResolver>>newHashSet());
        }

        this.resolvers = resolvers;
    }

    private void handle(ResolverEntry entry, Set<DamageResolver> resolvers, Set<Class<? extends DamageResolver>> seen) {
        seen = Sets.newHashSet(seen);
        for(Class<? extends DamageResolver> cls : entry.before) {
            if(!seen.add(cls)) {
                throw new IllegalStateException("infinite loop");
            }

            ResolverEntry clsEntry = this.getByClass(cls);
            if(clsEntry != null) {
                this.handle(clsEntry, resolvers, seen);
            }
        }
        resolvers.add(entry.resolver);
    }

    private @Nullable ResolverEntry getByClass(@Nonnull Class<? extends DamageResolver> resolverClass) {
        for(ResolverEntry entry : this.entries) {
            if(resolverClass.isInstance(entry.resolver)) {
                return entry;
            }
        }
        return null;
    }

    private final @Nonnull List<ResolverEntry> entries = Lists.newArrayList();
    private @Nullable Set<DamageResolver> resolvers = null;

    private static class ResolverEntry {
        public final DamageResolver resolver;
        public final Set<Class<? extends DamageResolver>> before;

        public ResolverEntry(DamageResolver resolver, Set<Class<? extends DamageResolver>> before) {
            this.resolver = resolver;
            this.before = before;
        }
    }
}
