package info.beastsoftware.beastcore.event;

import info.beastsoftware.hookcore.entity.BeastPlayer;
import lombok.Getter;

@Getter
public class ThrowableEggsGiveEvent extends VoidEvent {
    private final BeastPlayer player;
    private final int amount;

    public ThrowableEggsGiveEvent(BeastPlayer player, int amount) {
        this.player = player;
        this.amount = amount;
        this.autoCall();
    }
}
