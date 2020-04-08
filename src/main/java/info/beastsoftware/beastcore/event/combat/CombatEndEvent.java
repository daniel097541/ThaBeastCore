package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class CombatEndEvent extends CombatEvent {
    public CombatEndEvent(BeastPlayer player) {
        super(player);
    }
}
