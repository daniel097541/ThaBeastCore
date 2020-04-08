package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.grace.GraceTaskEndEvent;
import org.bukkit.Bukkit;

public class GraceTask implements Runnable {
    private int timeLeft;
    private final IConfig config;
    private boolean running;
    private int task;
    private final BeastCore plugin;
    private final String BASE_PATH = "Grace-Period.";
    private final int time;
    private final String TIME_LEFT_PATH = BASE_PATH + "left";
    private final String INITIAL_TIME = BASE_PATH + "initial-time";
    private final String RUNNING = BASE_PATH + "running";
    private final int saveTask;

    public GraceTask(IConfig config, BeastCore plugin) {
        this.config = config;
        this.time = config.getConfig().getInt(INITIAL_TIME);
        this.timeLeft = config.getConfig().getInt(TIME_LEFT_PATH);
        this.plugin = plugin;
        this.running = config.getConfig().getBoolean(RUNNING);
        this.startTask();
        int timeToSave = this.config.getConfig().getInt(BASE_PATH + "Auto-Save-Interval");
        this.saveTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin,() -> {
            this.saveTaskStatus();
            this.saveTimeLeft();
        }, 0L, timeToSave * 20L);
    }

    public boolean isRunning() {
        return running;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void pause() {
        this.running = false;
        this.saveTaskStatus();
    }

    public void resume() {
        this.running = true;
        this.saveTaskStatus();
    }

    public void saveTaskStatus() {
        this.config.getConfig().set(RUNNING, this.isRunning());
        this.config.save();
    }

    public void saveTimeLeft() {
        this.config.getConfig().set(TIME_LEFT_PATH, this.timeLeft);
        this.config.save();
    }

    public void startTask() {
        this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, this, 0L, 20L);
    }

    public void cancelTask() {
        if (this.task == -1) {
            return;
        }
        Bukkit.getScheduler().cancelTask(this.task);
        this.task = -1;
    }


    public void resetGraceTime() {
        this.timeLeft = time;
    }

    private void callEndEvent() {
        Bukkit.getPluginManager().callEvent(new GraceTaskEndEvent());
    }

    @Override
    public void run() {
        if (!this.isRunning()) {
            return;
        }

        if (timeLeft <= 0) {
            this.callEndEvent();
        } else {
            this.timeLeft--;
        }

    }
}
