package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class CombatPussyDetectedEvent extends CombatEvent {


    public CombatPussyDetectedEvent(BeastPlayer target) {
        super(target);
    }
}
