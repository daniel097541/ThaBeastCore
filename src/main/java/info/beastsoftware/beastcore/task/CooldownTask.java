package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.BeastCore;
import org.bukkit.Bukkit;

public interface CooldownTask {


    int getLeft();

    void decrement();

    void callEvent();

    default void schedule(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(BeastCore.getInstance(), this::run, 20L);
    }

    default void run(){

        if(this.getLeft() <= 0){
            callEvent();
        }

        else{
            decrement();
            schedule();
        }

    }

}
