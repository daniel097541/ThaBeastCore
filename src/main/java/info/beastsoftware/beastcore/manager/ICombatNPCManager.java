package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.entity.ICombatNPC;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface ICombatNPCManager {


    /**
     * Adds a player that is doing combat log
     *
     * @param player
     */
    void addPussy(BeastPlayer player, int timeToDie);

    /**
     * Gets the npc of a player disconnected
     *
     * @param player
     * @return
     */
    ICombatNPC getNPCOfPlayer(OfflinePlayer player);

    /**
     * Returns true if the player did combat log
     *
     * @param player
     * @return
     */
    boolean isAPussy(OfflinePlayer player);


    /**
     * Get a list with all the combat loggers
     *
     * @return
     */
    List<ICombatNPC> getAllNPCs();


    /**
     * Decreases time to die for all players that did combat log
     */
    default void decreaseTimeToDie() {
        this.getAllNPCs()
                .parallelStream()
                .forEach(ICombatNPC::decreaseTimeToDie);
    }


    /**
     * Removes a npc from the list
     *
     * @param npc
     */
    void removeNPC(ICombatNPC npc);


    /**
     * Looks for a npc by its entity
     *
     * @param entity
     * @return
     */
    default boolean isNPC(Entity entity) {
        return this.getAllNPCs()
                .parallelStream()
                .anyMatch(npc -> npc.getNPC().equals(entity));
    }

}
