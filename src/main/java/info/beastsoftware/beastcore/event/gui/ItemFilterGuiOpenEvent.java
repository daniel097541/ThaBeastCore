package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class ItemFilterGuiOpenEvent extends GuiOpenEvent {
    public ItemFilterGuiOpenEvent(BeastPlayer player) {
        super(player);
    }
}
