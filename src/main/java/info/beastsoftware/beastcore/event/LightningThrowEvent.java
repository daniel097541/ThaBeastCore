package info.beastsoftware.beastcore.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LightningThrowEvent extends VoidEvent {

    private final Player thrower;
    private final Entity target;


    public LightningThrowEvent(Player thrower, Entity target) {
        this.thrower = thrower;
        this.target = target;
    }

    public Player getThrower() {
        return thrower;
    }

    public Entity getTarget() {
        return target;
    }
}
