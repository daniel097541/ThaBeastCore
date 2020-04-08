package info.beastsoftware.beastcore.manager;

import com.massivecraft.massivecore.xlib.mongodb.util.Hash;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.task.CombatCooldownTask;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface ICombatCooldownManager {

    HashMap<UUID, CombatCooldownTask> getCooldowns();


    default boolean isOnCooldown(BeastPlayer player){
        return this.getCooldowns()
                .keySet()
                .contains(player.getUuid());
    }


    default CombatCooldownTask getCombatCooldownOfPlayer(BeastPlayer player){
        return this.getCooldowns()
                .get(player.getUuid());
    }

    default void restartCooldown(BeastPlayer player){
        if(!this.isOnCooldown(player)) return;
        this.getCombatCooldownOfPlayer(player)
                .restart();
    }

    default void removeCooldownOfPlayer(BeastPlayer player){
        this.getCooldowns()
                .remove(player.getUuid());
    }

    default void cancelCooldownOfPlayer(BeastPlayer player){
        if(!this.isOnCooldown(player)) return;
        this.getCombatCooldownOfPlayer(player)
                .cancel();
    }

    default void clearAllCooldows(){
        this.getCooldowns()
                .values()
                .forEach(CombatCooldownTask::cancel);

        this.getCooldowns()
                .clear();
    }


    default void addCooldown(BeastPlayer player, int time){
        this.getCooldowns()
                .put(player.getUuid(), new CombatCooldownTask(time, player, BeastCore.getInstance()));
    }

    default void endCooldown(BeastPlayer player){
        this.getCombatCooldownOfPlayer(player)
                .end();
    }

}
