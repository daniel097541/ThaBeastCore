package info.beastsoftware.beastcore.gui.event;

import info.beastsoftware.beastcore.event.VoidEvent;
import info.beastsoftware.beastcore.gui.ISimpleGui;
import org.bukkit.entity.Player;

public abstract class SimpleGUIEvent extends VoidEvent {

    private final ISimpleGui gui;
    private final Player player;


    public SimpleGUIEvent(ISimpleGui gui, Player player) {
        this.gui = gui;
        this.player = player;
    }


    public ISimpleGui getGui() {
        return gui;
    }

    public Player getPlayer() {
        return player;
    }
}
