package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class EditDropsGUIOpenEvent extends GuiOpenEvent {
    public EditDropsGUIOpenEvent(Player player) {
        super(player);
    }
}
