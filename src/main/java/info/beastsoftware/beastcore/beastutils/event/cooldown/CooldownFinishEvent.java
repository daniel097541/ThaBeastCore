package info.beastsoftware.beastcore.beastutils.event.cooldown;

import org.bukkit.entity.Player;

public abstract class CooldownFinishEvent extends CooldownEvent {

    public CooldownFinishEvent(Player player) {
        super(player);
    }
}
