package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class CombatStartEvent extends CombatEvent {
    public CombatStartEvent(BeastPlayer target) {
        super(target);
    }
}
