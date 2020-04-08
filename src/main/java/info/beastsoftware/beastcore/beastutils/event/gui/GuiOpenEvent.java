package info.beastsoftware.beastcore.beastutils.event.gui;

import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class GuiOpenEvent extends Event implements APIAccessor {
    private static final HandlerList handlers = new HandlerList();

    private BeastPlayer player;

    public GuiOpenEvent(Player player) {
        this.player = this.getPlayer(player);
    }

    public GuiOpenEvent(BeastPlayer player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public BeastPlayer getPlayer() {
        return player;
    }
}
