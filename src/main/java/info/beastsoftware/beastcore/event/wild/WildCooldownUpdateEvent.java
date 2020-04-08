package info.beastsoftware.beastcore.event.wild;

import info.beastsoftware.beastcore.beastutils.event.cooldown.CooldownUpdateEvent;
import org.bukkit.entity.Player;

public class WildCooldownUpdateEvent extends CooldownUpdateEvent {
    public WildCooldownUpdateEvent(Player player, int timeLeft) {
        super(player, timeLeft);
    }
}
