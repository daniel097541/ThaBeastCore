package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class MainGuiOpenEvent extends GuiOpenEvent {
    public MainGuiOpenEvent(Player player) {
        super(player);
    }
}
