package info.beastsoftware.beastcore.beastutils.event.cooldown;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class GraceTaskEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private int timeLeft;

    public GraceTaskEvent(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
