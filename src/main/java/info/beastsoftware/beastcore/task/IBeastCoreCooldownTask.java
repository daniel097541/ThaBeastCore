package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.entity.APIAccessor;
import org.bukkit.Bukkit;

public interface IBeastCoreCooldownTask extends Runnable, APIAccessor {



    int getLeft();

    void reduce();

    default void iterate(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.getBeastCore(), this, 20L);
    }

    void callEndEvent();


    default void run(){


        if(this.getLeft() <= 0){
            this.callEndEvent();
        }
        else{
            reduce();
            this.iterate();
        }

    }





}
