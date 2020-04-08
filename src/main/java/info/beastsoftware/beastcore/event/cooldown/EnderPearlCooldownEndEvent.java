package info.beastsoftware.beastcore.event.cooldown;

import info.beastsoftware.beastcore.event.VoidEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class EnderPearlCooldownEndEvent extends VoidEvent {

    private final BeastPlayer player;


    public EnderPearlCooldownEndEvent(BeastPlayer player) {
        this.player = player;
    }

    public BeastPlayer getPlayer() {
        return player;
    }
}
