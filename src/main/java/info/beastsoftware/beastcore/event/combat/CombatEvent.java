package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CombatEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final BeastPlayer target;
    private boolean cancelled;

    public CombatEvent(BeastPlayer target) {
        this.target = target;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public BeastPlayer getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
