package info.beastsoftware.beastcore.printer;

import info.beastsoftware.beastcore.event.ShowMoneySpentInPrinterEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShowMoneyTask implements Runnable {

    private final BeastPlayer player;


    public ShowMoneyTask(BeastPlayer player) {
        this.player = player;
    }


    @Override
    public void run() {
        Bukkit.getPluginManager().callEvent(new ShowMoneySpentInPrinterEvent(player));
    }
}
