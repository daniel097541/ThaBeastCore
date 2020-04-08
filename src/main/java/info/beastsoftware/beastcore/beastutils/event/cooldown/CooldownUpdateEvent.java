package info.beastsoftware.beastcore.beastutils.event.cooldown;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CooldownUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private int timeLeft;


    public CooldownUpdateEvent(Player player, int timeLeft) {
        this.player = player;
        this.timeLeft = timeLeft;
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

    public int getTimeLeft() {
        return timeLeft;
    }
}
