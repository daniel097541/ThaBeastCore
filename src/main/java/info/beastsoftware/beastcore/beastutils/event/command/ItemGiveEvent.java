package info.beastsoftware.beastcore.beastutils.event.command;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ItemGiveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();


    private BeastPlayer player;
    private int amount;


    public ItemGiveEvent(BeastPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public int getAmount() {
        return amount;
    }

    public BeastPlayer getPlayer() {
        return player;
    }
}
