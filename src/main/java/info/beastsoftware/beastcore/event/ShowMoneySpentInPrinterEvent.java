package info.beastsoftware.beastcore.event;

import info.beastsoftware.hookcore.entity.BeastPlayer;

public class ShowMoneySpentInPrinterEvent extends VoidEvent {

    private final BeastPlayer player;


    public ShowMoneySpentInPrinterEvent(BeastPlayer player) {
        this.player = player;
    }

    public BeastPlayer getPlayer() {
        return player;
    }
}
