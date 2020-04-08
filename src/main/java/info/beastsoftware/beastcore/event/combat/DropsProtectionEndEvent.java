package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

public class DropsProtectionEndEvent extends CombatEvent {

    private final Set<Entity> drops;

    public DropsProtectionEndEvent(BeastPlayer target, Set<Entity> drops) {
        super(target);
        this.drops = drops;
    }


    public Set<Entity> getDrops() {
        return drops;
    }
}
