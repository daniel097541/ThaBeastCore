package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.task.CooldownTask;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

import java.util.Map;

public interface ICooldownManager {


    Map<BeastPlayer, CooldownTask> getCooldowns();

    default boolean isOnCooldown(BeastPlayer player) {
        return getCooldowns().containsKey(player);
    }

    default int getLeft(BeastPlayer player) {
        try {
            return getCooldowns().get(player).getLeft();
        } catch (Exception e) {
            return -1;
        }
    }

    default void add(BeastPlayer player, CooldownTask cooldownTask) {
        this.getCooldowns().put(player, cooldownTask);
    }

    default void remove(BeastPlayer player){
        this.getCooldowns().remove(player);
    }

}
