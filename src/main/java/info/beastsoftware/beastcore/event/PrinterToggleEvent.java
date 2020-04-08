package info.beastsoftware.beastcore.event;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrinterToggleEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private boolean on;

    private BeastPlayer player;


    public PrinterToggleEvent(boolean on, BeastPlayer player) {
        this.on = on;
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isOn() {
        return on;
    }

    public BeastPlayer getPlayer() {
        return player;
    }
}
