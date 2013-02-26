package tc.oc.tracker.trackers.base.gravity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Attack {
    public static enum Cause { HIT, SHOOT, SPLEEF }
    public static enum From { FLOOR, LADDER, WATER }

    final public Player victim;
    final public LivingEntity attacker;
    final public Cause cause;
    final public From from;
    final public long time;
    public boolean wasAttacked;
    public long onGroundTime;
    public boolean isSwimming;
    public long swimmingTime;
    public boolean isClimbing;
    public long climbingTime;
    public boolean isInLava;
    public long inLavaTime;

    Attack(LivingEntity attacker, Cause cause, Player victim, From from, long time) {
        this.attacker = attacker;
        this.cause = cause;
        this.victim = victim;
        this.from = from;
        this.time = this.swimmingTime = this.climbingTime = this.onGroundTime = time;
    }
}