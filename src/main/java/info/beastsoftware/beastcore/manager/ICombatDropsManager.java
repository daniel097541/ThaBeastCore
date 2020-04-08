package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.beastcore.task.CombatDropsTask;
import org.bukkit.entity.Player;

import java.util.List;

public interface ICombatDropsManager extends APIAccessor {



    List<CombatDropsTask> getCombatDropsTasks();


    default void addCombatDropsTask(Player player){

    }

}
