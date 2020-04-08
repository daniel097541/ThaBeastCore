package info.beastsoftware.beastcore.event.wild;

import info.beastsoftware.beastcore.beastutils.event.cooldown.CooldownEvent;
import org.bukkit.entity.Player;

public class WildCooldownFinishEvent extends CooldownEvent {
    public WildCooldownFinishEvent(Player player) {
        super(player);
    }
}
