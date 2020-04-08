package info.beastsoftware.beastcore.event.itemgiveevent;

import info.beastsoftware.beastcore.beastutils.event.command.ItemGiveEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class ChunkBustersGiveEvent extends ItemGiveEvent {

    private int radius;

    public ChunkBustersGiveEvent(BeastPlayer player, int amount, int radius) {
        super(player, amount);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
