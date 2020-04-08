package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class MainMenuOpenEvent extends GuiOpenEvent {
    public MainMenuOpenEvent(Player player) {
        super(player);
    }
}
