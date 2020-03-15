package info.beastsoftware.core.common;

import org.bukkit.Bukkit;

public interface Syncer extends PluginAccessor{

    default void runAsync(Runnable task){
        this.runAsyncLater(task, 0L);
    }

    default void runAsyncLater(Runnable task, long delay){
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.getPlugin(), task, delay);
    }

    default void runSyncLater(Runnable task, long delay){
        Bukkit.getScheduler().runTaskLater(this.getPlugin(), task, delay);
    }

}
