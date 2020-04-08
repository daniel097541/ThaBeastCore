package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class CropHopperGUIOpenEvent extends GuiOpenEvent {
    public CropHopperGUIOpenEvent(Player player) {
        super(player);
    }
}
