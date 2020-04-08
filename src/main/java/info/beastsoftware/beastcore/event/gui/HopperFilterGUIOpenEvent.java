package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class HopperFilterGUIOpenEvent extends GuiOpenEvent {
    public HopperFilterGUIOpenEvent(Player player) {
        super(player);
    }
}
