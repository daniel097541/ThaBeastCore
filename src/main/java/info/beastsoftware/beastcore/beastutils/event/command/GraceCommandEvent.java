package info.beastsoftware.beastcore.beastutils.event.command;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class GraceCommandEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private CommandSender player;

    public GraceCommandEvent(CommandSender player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CommandSender getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
