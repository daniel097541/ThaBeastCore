package info.beastsoftware.beastcore.beastutils.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class BroadcastableTask implements IBroadcastableTask, Runnable {

    protected Player player;
    private int left;
    private int initialTime;
    private int task;
    private Plugin plugin;

    public BroadcastableTask(int initialTime, Player player, Plugin plugin) {
        this.initialTime = initialTime;
        this.left = initialTime;
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void startTask() {
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }

    @Override
    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(task);
        callCancelEvent();
    }

    @Override
    public void endTask() {
        Bukkit.getScheduler().cancelTask(task);
        callEndEvent();
    }


    @Override
    public void run() {
        if (left <= 0) {
            endTask();
            return;
        }

        callUpdateEvent();
        left--;
    }
}
