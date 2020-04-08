package info.beastsoftware.beastcore.beastutils.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class NoBroadcastableTask implements INoBroadcastableTask, Runnable {


    protected int left;
    private boolean running;
    private int task;
    private Plugin plugin;

    public NoBroadcastableTask(int left, Plugin plugin) {
        this.left = left;
        this.plugin = plugin;
    }

    @Override
    public void initTask() {
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 20L, 20L);
        running = true;
        callStartEvent();
    }

    @Override
    public void cancelTask() {
        this.running = false;
        Bukkit.getScheduler().cancelTask(task);
        callCancelEvent();
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void endTask() {
        this.running = false;
        Bukkit.getScheduler().cancelTask(task);
        callEndEvent();
    }

    @Override
    public void toggle() {
        this.running = !running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {

        if (left <= 0) {
            this.endTask();
            return;
        }

        left--;
    }
}
