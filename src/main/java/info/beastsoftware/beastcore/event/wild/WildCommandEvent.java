package info.beastsoftware.beastcore.event.wild;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WildCommandEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    private int noMoveCooldown;


    public WildCommandEvent(Player player, int noMoveCooldown) {
        this.player = player;
        this.noMoveCooldown = noMoveCooldown;
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

    public int getNoMoveCooldown() {
        return noMoveCooldown;
    }
}
