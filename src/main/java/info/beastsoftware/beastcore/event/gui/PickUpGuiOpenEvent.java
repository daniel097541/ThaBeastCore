package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class PickUpGuiOpenEvent extends GuiOpenEvent {
    public PickUpGuiOpenEvent(Player player) {
        super(player);
    }
}
