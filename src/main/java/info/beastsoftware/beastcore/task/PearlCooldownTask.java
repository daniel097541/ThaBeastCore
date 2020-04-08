package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.event.cooldown.EnderPearlCooldownEndEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PearlCooldownTask implements CooldownTask{

    private int left;

    private final BeastPlayer player;

    public PearlCooldownTask(int left, BeastPlayer player) {
        this.left = left;
        this.player = player;
        this.run();
    }


    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void decrement() {
        this.left--;
    }

    @Override
    public void callEvent() {
        Bukkit.getPluginManager().callEvent(new EnderPearlCooldownEndEvent(player));
    }
}
