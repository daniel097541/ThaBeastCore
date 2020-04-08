package info.beastsoftware.beastcore.printer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Singleton
public class PrinterManager implements IPrinterManager {


    private final THashMap<BeastPlayer, ItemStack[]> inventoryContents = new THashMap<>();

    private final THashMap<BeastPlayer, ItemStack[]> armorContents = new THashMap<>();

    private final TObjectIntHashMap<BeastPlayer> expMap = new TObjectIntHashMap<>();

    private final THashMap<UUID, BeastPlayer> playersInPrinter = new THashMap<>();

    private final Multimap<BeastPlayer, Block> blocksPlacedMap = ArrayListMultimap.create();

    private final THashMap<UUID, BeastPlayer> noFallPlayers = new THashMap<>();

    private final TObjectIntHashMap<BeastPlayer> spentInPrinter = new TObjectIntHashMap<>();

    private final TObjectIntHashMap<BeastPlayer> showMoneyTasks = new TObjectIntHashMap<>();

    private final BeastCore plugin;

    @Inject
    public PrinterManager(BeastCore plugin) {
        this.plugin = plugin;
    }


    @Override
    public THashMap<BeastPlayer, ItemStack[]> getArmorContentsMap() {
        return this.armorContents;
    }

    @Override
    public THashMap<BeastPlayer, ItemStack[]> getInventoryContentsMap() {
        return this.inventoryContents;
    }

    @Override
    public TObjectIntHashMap<BeastPlayer> getExpMap() {
        return this.expMap;
    }

    @Override
    public THashMap<UUID, BeastPlayer> getPlayersInPrinter() {
        return this.playersInPrinter;
    }

    @Override
    public Multimap<BeastPlayer, Block> getBlocksPlaced() {
        return this.blocksPlacedMap;
    }

    @Override
    public THashMap<UUID, BeastPlayer> getNoFallPlayers() {
        return this.noFallPlayers;
    }

    @Override
    public BeastCore getPlugin() {
        return this.plugin;
    }

    @Override
    public TObjectIntHashMap<BeastPlayer> getSpentInPrinter() {
        return this.spentInPrinter;
    }

    @Override
    public TObjectIntHashMap<BeastPlayer> getShowMoneyTasks() {
        return showMoneyTasks;
    }
}
