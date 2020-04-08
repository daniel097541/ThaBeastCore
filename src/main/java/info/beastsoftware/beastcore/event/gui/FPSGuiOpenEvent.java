package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class FPSGuiOpenEvent extends GuiOpenEvent {
    public FPSGuiOpenEvent(Player player) {
        super(player);
    }
}
