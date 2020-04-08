package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.task.BroadcastableTask;
import info.beastsoftware.beastcore.event.wild.WildCooldownCancelEvent;
import info.beastsoftware.beastcore.event.wild.WildCooldownFinishEvent;
import info.beastsoftware.beastcore.event.wild.WildCooldownUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WildBroadcastableTask extends BroadcastableTask {


    public WildBroadcastableTask(int initialTime, Player player) {
        super(initialTime, player, BeastCore.getInstance());
    }

    @Override
    public void callUpdateEvent() {
        Bukkit.getPluginManager().callEvent(new WildCooldownUpdateEvent(player, getLeft()));
    }

    @Override
    public void callCancelEvent() {
        Bukkit.getPluginManager().callEvent(new WildCooldownCancelEvent(player));
    }

    @Override
    public void callEndEvent() {
        Bukkit.getPluginManager().callEvent(new WildCooldownFinishEvent(player));
    }
}
