package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.event.combat.CombatEndEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CombatCooldownTask implements Runnable {



    private final int time;
    private final BeastPlayer player;
    private int timeLeft;
    private final int task;

    public CombatCooldownTask(int time, BeastPlayer player, BeastCore plugin) {
        this.time = time;
        this.player = player;
        this.timeLeft = time;
        this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, this, 0L,20L);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void restart(){
        this.timeLeft = time;
    }

    public void cancel(){
        Bukkit.getScheduler().cancelTask(this.task);
    }

    public void end(){
        Bukkit.getPluginManager().callEvent(new CombatEndEvent(player));
        this.cancel();
    }


    @Override
    public void run() {

        if(timeLeft > 0){
            timeLeft--;
        }

        else{
            end();
        }
    }
}
