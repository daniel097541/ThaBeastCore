package info.beastsoftware.beastcore.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class VoidEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
