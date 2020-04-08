package info.beastsoftware.beastcore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionCreatedEvent extends Event implements Cancellable {


    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private String factionId;
    private boolean cancelled;

    public FactionCreatedEvent(Player player, String factionId) {
        this.player = player;
        this.factionId = factionId;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public String getFactionId() {
        return factionId;
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
