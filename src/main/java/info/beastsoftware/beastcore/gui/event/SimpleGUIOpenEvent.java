package info.beastsoftware.beastcore.gui.event;

import info.beastsoftware.beastcore.gui.ISimpleGui;
import org.bukkit.entity.Player;

public abstract class SimpleGUIOpenEvent extends SimpleGUIEvent{

    public SimpleGUIOpenEvent(ISimpleGui gui, Player player) {
        super(gui, player);
    }
}
