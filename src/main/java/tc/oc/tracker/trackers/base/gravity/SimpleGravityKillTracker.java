package tc.oc.tracker.trackers.base.gravity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;

import tc.oc.tracker.base.AbstractTracker;
import tc.oc.tracker.plugin.TrackerPlugin;
import tc.oc.tracker.timer.TickTimer;
import tc.oc.tracker.util.PlayerBlockChecker;

import com.google.common.collect.Maps;

public class SimpleGravityKillTracker extends AbstractTracker {
    public final HashMap<Location, BrokenBlock> brokenBlocks = new HashMap<Location, BrokenBlock>();
    public final HashMap<Player, Attack> attacks = new HashMap<Player, Attack>();

    private final Map<Player, Attack> attackCache = Maps.newHashMap();

    public static final long MAX_KNOCKBACK_TIME = 20;
    public static final long MAX_SPLEEF_TIME = 20;
    public static final long MAX_ON_GROUND_TIME = 10;
    public static final long MAX_SWIMMING_TIME = 20;
    public static final long MAX_CLIMBING_TIME = 10;

    private final TrackerPlugin plugin;
    private final TickTimer timer;

    public SimpleGravityKillTracker(TrackerPlugin plugin, TickTimer timer) {
        this.plugin = plugin;
        this.timer = timer;
    }

    public void clear(@Nonnull World world) {
        Iterator<Location> blocks = this.brokenBlocks.keySet().iterator();
        while(blocks.hasNext()) {
            Location loc = blocks.next();
            if(loc.getWorld() == world) {
                blocks.remove();
            }
        }

        Iterator<Player> players = this.attacks.keySet().iterator();
        while(players.hasNext()) {
            Player player = players.next();
            if(player.getWorld() == world) {
                players.remove();
            }
        }
    }

    private boolean isSupported(final Attack attack) {
        return attack.isClimbing || attack.isSwimming || attack.victim.isOnGround();
    }

    private void cancelFall(Attack attack) {
        this.attacks.remove(attack.victim);
    }

    private void checkAttackCancelled(final Attack attack, long time) {
        if(this.attacks.get(attack.victim) == attack) {
            if(attack.wasAttacked) {
                if (!attack.isInLava) {
                    if (attack.victim.isOnGround() && time - attack.onGroundTime > MAX_ON_GROUND_TIME) this.cancelFall(attack);
                    if (attack.isSwimming && time - attack.swimmingTime > MAX_SWIMMING_TIME) this.cancelFall(attack);
                    if (attack.isClimbing && time - attack.climbingTime > MAX_CLIMBING_TIME) this.cancelFall(attack);
                }
            } else {
                if (attack.victim.isOnGround() && time - attack.time > MAX_KNOCKBACK_TIME) this.cancelFall(attack);
                if (attack.isSwimming && time - attack.time > MAX_KNOCKBACK_TIME) this.cancelFall(attack);
                if (attack.isClimbing && time - attack.time > MAX_KNOCKBACK_TIME) this.cancelFall(attack);
            }
        }
    }

    private void scheduleCheckAttackCancelled(final Attack attack, long delay) {
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
            public void run() {
                SimpleGravityKillTracker.this.checkAttackCancelled(attack, SimpleGravityKillTracker.this.timer.getTicks());
            }
        }, delay + 1);
    }

    private void checkKnockback(final Attack attack, long time) {
        if(!attack.wasAttacked && !this.isSupported(attack) && time - attack.time <= MAX_KNOCKBACK_TIME) {
            attack.wasAttacked = true;
        }
    }

    public void victimOnGround(final Attack attack, long time) {
        this.scheduleCheckAttackCancelled(attack, MAX_ON_GROUND_TIME + 1);
    }

    public void victimOffGround(Attack attack, long time) {
        this.checkKnockback(attack, time);
    }

    public void victimOnLadder(Attack attack, long time) {
        this.scheduleCheckAttackCancelled(attack, MAX_CLIMBING_TIME + 1);
    }

    public void victimOffLadder(Attack attack, long time) {
        this.checkKnockback(attack, time);
    }

    public void victimInWater(Attack attack, long time) {
        this.scheduleCheckAttackCancelled(attack, MAX_SWIMMING_TIME + 1);
    }

    public void victimOutOfWater(Attack attack, long time) {
        this.checkKnockback(attack, time);
    }

    public void victimInLava(Attack attack, long time) {
    }

    public void victimOutOfLava(Attack attack, long time) {
        this.checkAttackCancelled(attack, time);
    }

    public void nonVictimOffGround(Player player, long time) {
        BrokenBlock brokenBlock = BrokenBlock.findBlockBrokenUnderPlayer(player, time, this.brokenBlocks);
        if(brokenBlock != null) {
            Attack attack = new Attack(brokenBlock.breaker, Attack.Cause.SPLEEF, player, Attack.From.FLOOR, brokenBlock.time );

            attack.isClimbing = PlayerBlockChecker.isClimbing(player);
            attack.isSwimming = PlayerBlockChecker.isSwimming(player, Material.WATER);
            attack.isInLava = PlayerBlockChecker.isSwimming(player, Material.LAVA);

            attack.wasAttacked = true;

            this.attacks.put(player, attack);
        }
    }

    public Attack playerAttacked(Player player, Entity entity, long time) {
        if(this.attacks.containsKey(player)) {
            return null;
        }

        boolean isInLava = PlayerBlockChecker.isSwimming(player, Material.LAVA);

        if(isInLava) {
            return null;
        }

        boolean isClimbing = PlayerBlockChecker.isClimbing(player);
        boolean isSwimming = PlayerBlockChecker.isSwimming(player, Material.WATER);

        Attack.Cause cause;
        if(entity instanceof Projectile) {
            entity = ((Projectile) entity).getShooter();
            cause = Attack.Cause.SHOOT;
        } else {
            cause = Attack.Cause.HIT;
        }

        if(!(entity instanceof LivingEntity)) {
            return null;
        }

        Attack.From from = null;
        if(isClimbing) {
            from = Attack.From.LADDER;
        } else if(isSwimming) {
            from = Attack.From.WATER;
        } else {
            from = Attack.From.FLOOR;
        }

        Attack attack = new Attack((LivingEntity) entity, cause, player, from, time);

        attack.isClimbing = isClimbing;
        attack.isSwimming = isSwimming;
        attack.isInLava = isInLava;

        attack.wasAttacked = !this.isSupported(attack);

        this.attackCache.put(player, attack); // cache the attack so we can save it later

        return attack;
    }

    public Attack getAndRemoveCachedAttack(Player player) {
        return this.attackCache.remove(player);
    }

    public void storeAttack(Player player, Attack attack) {
        this.attacks.put(player, attack);

        if(!attack.wasAttacked) {
            this.scheduleCheckAttackCancelled(attack, MAX_KNOCKBACK_TIME + 1);
        }
    }

    public boolean wasAttackFatal(Attack attack, EntityDamageEvent.DamageCause damageCause, long time) {
        switch(damageCause) {
            case VOID:
            case FALL:
            case LAVA:
            case SUICIDE:
                return true;

            case FIRE_TICK:
                return attack.isInLava;

            default:
                return false;
        }
    }
}
