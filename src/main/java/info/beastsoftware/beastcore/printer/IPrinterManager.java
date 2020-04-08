package info.beastsoftware.beastcore.printer;

import com.google.common.collect.Multimap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface IPrinterManager {


    THashMap<BeastPlayer, ItemStack[]> getArmorContentsMap();

    THashMap<BeastPlayer, ItemStack[]> getInventoryContentsMap();

    TObjectIntHashMap<BeastPlayer> getExpMap();

    THashMap<UUID, BeastPlayer> getPlayersInPrinter();

    Multimap<BeastPlayer, Block> getBlocksPlaced();

    THashMap<UUID, BeastPlayer> getNoFallPlayers();

    BeastCore getPlugin();

    TObjectIntHashMap<BeastPlayer> getSpentInPrinter();

    TObjectIntHashMap<BeastPlayer> getShowMoneyTasks();


    default void addShowMoneyTask(BeastPlayer player, long time) {
        ShowMoneyTask showMoneyTask = new ShowMoneyTask(player);
        int task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(getPlugin(), showMoneyTask, time*20L, time*20L);
        this.getShowMoneyTasks().put(player, task);
    }

    default void removeShowMoneyTask(BeastPlayer player) {
        Bukkit.getScheduler().cancelTask(this.getShowMoneyTasks().get(player));
        this.getShowMoneyTasks().remove(player);
    }


    default void disableForAll() {
        this.getPlayersInPrinter()
                .values()
                .forEach(this::disablePrinterForPlayer);
    }


    default boolean isPlayerInPrinter(BeastPlayer player) {
        return this.getPlayersInPrinter()
                .contains(player.getUuid());
    }


    default boolean hasPlayerPlacedBlockWhileInPrinter(BeastPlayer player, Block block) {
        return this.getBlocksPlaced().containsEntry(player, block);
    }


    default void disablePrinterForPlayer(BeastPlayer player) {

        player.getBukkitPlayer().closeInventory();

        player.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);

        ItemStack[] inventoryContents = this.getInventoryContentsMap().get(player);
        ItemStack[] armorContents = this.getArmorContentsMap().get(player);
        int exp = this.getExpMap().get(player);

        //restore gamemode
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getBukkitPlayer().updateInventory();

        //restore exp and inventory
        player.getBukkitPlayer().setTotalExperience(exp);
        player.getInventory().setContents(inventoryContents);
        player.getInventory().setArmorContents(armorContents);

        //remove from all maps
        this.getPlayersInPrinter().remove(player.getUuid());
        this.getExpMap().remove(player);
        this.getBlocksPlaced().removeAll(player);
        this.getArmorContentsMap().remove(player);
        this.getInventoryContentsMap().remove(player);
        this.getSpentInPrinter().remove(player);

    }


    default void enablePrinterForPlayer(BeastPlayer player) {

        player.getBukkitPlayer().closeInventory();

        ItemStack[] inventoryContents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        int exp = player.getBukkitPlayer().getTotalExperience();

        //store inventory and exp
        this.getPlayersInPrinter().put(player.getUuid(), player);
        this.getInventoryContentsMap().put(player, inventoryContents);
        this.getArmorContentsMap().put(player, armorContents);
        this.getExpMap().put(player, exp);
        this.getSpentInPrinter().put(player, 0);


        //clear inventory and exp
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getBukkitPlayer().setTotalExperience(0);

        player.getBukkitPlayer().setGameMode(GameMode.CREATIVE);
        player.getBukkitPlayer().updateInventory();
    }


    default void playerPlacedBlock(BeastPlayer player, Block block) {
        this.getBlocksPlaced().put(player, block);
    }

    default void playerBrokeBlock(BeastPlayer player, Block block) {
        this.getBlocksPlaced().remove(player, block);
    }


    default boolean hasPlayerFallDamageDisabled(BeastPlayer player) {
        return this.getNoFallPlayers().contains(player.getUuid());
    }


    default int getSpentInPrinterByPlayer(BeastPlayer player) {
        return this.getSpentInPrinter().get(player);
    }


    default void spentMoneyInPrinter(BeastPlayer player, int money) {
        int spentInPrinter = this.getSpentInPrinterByPlayer(player);
        this.getSpentInPrinter().put(player, spentInPrinter + money);
    }


    default void addPlayerToNoFallDamage(BeastPlayer player) {
        this.getNoFallPlayers().put(player.getUuid(), player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), () -> this.getNoFallPlayers().remove(player.getUuid()), 400L);
    }
}
