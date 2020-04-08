package info.beastsoftware.beastcore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JellyLegsToogleEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private boolean on;

    private Player player;


    public JellyLegsToogleEvent(boolean on, Player player) {
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

    public Player getPlayer() {
        return player;
    }
}
