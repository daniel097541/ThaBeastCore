package info.beastsoftware.beastcore.event.wild;

import info.beastsoftware.beastcore.beastutils.event.cooldown.CooldownEvent;
import org.bukkit.entity.Player;

public class WildCooldownCancelEvent extends CooldownEvent {
    public WildCooldownCancelEvent(Player player) {
        super(player);
    }
}
