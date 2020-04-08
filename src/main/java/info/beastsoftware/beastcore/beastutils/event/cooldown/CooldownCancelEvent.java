package info.beastsoftware.beastcore.beastutils.event.cooldown;

import org.bukkit.entity.Player;


public abstract class CooldownCancelEvent extends CooldownEvent {

    public CooldownCancelEvent(Player player) {
        super(player);
    }
}
