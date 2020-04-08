package info.beastsoftware.beastcore.event.itemgiveevent;

import info.beastsoftware.beastcore.beastutils.event.command.ItemGiveEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class CropHopperGiveEvent extends ItemGiveEvent {
    public CropHopperGiveEvent(BeastPlayer player, int amount) {
        super(player, amount);
    }
}
