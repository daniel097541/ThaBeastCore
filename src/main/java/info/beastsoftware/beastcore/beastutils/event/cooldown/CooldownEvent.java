package info.beastsoftware.beastcore.beastutils.event.cooldown;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CooldownEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    public CooldownEvent(Player player) {
        this.player = player;
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
}
