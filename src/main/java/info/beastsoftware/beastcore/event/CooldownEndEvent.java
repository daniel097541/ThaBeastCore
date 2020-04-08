package info.beastsoftware.beastcore.event;

import info.beastsoftware.beastcore.struct.CooldownType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CooldownEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private String playerName;
    private CooldownType cdType;

    public CooldownEndEvent(String playerName, CooldownType cdType) {
        this.playerName = playerName;
        this.cdType = cdType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public CooldownType getCdType() {
        return cdType;
    }
}
