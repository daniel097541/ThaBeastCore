package info.beastsoftware.beastcore.gui.event;

import info.beastsoftware.beastcore.gui.ISimpleGui;
import org.bukkit.entity.Player;

public class CombatGUIOpenEvent extends SimpleGUIOpenEvent {

    public CombatGUIOpenEvent(ISimpleGui gui, Player player) {
        super(gui, player);
    }
}
