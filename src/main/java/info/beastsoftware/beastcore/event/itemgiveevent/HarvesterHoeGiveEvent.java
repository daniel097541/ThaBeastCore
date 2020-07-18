package info.beastsoftware.beastcore.event.itemgiveevent;

import info.beastsoftware.beastcore.beastutils.event.command.ItemGiveEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;

public class HarvesterHoeGiveEvent extends ItemGiveEvent {
    public HarvesterHoeGiveEvent(BeastPlayer player, int amount) {
        super(player, amount);
    }
}
