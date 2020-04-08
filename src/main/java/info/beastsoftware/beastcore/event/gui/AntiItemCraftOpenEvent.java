package info.beastsoftware.beastcore.event.gui;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import org.bukkit.entity.Player;

public class AntiItemCraftOpenEvent extends GuiOpenEvent {
    public AntiItemCraftOpenEvent(Player player) {
        super(player);
    }
}
