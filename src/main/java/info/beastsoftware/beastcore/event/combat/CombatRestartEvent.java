package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class CombatRestartEvent extends CombatEvent {
    public CombatRestartEvent(BeastPlayer target) {
        super(target);
    }
}
