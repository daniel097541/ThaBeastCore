package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.PrinterToggleEvent;
import info.beastsoftware.beastcore.event.ShowMoneySpentInPrinterEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.IEconomyHook;
import info.beastsoftware.beastcore.manager.NBTManager;
import info.beastsoftware.beastcore.printer.IPrinterManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.List;

public class PrinterFeatureListener extends AbstractFeatureListener {

    private final IPrinterManager printerManager;
    private final NBTManager nbtManager = new NBTManager() {
    };

    public PrinterFeatureListener(IConfig config, IEconomyHook economyHook, IPrinterManager printerManager) {
        super(config, FeatureType.PRINTER);
        this.printerManager = printerManager;

        if (economyHook == null) {
            return;
        }
        this.economyUtil = new EconomyUtil(economyHook.getEconomy());
        essentials = config.getConfig().getBoolean("Settings.use-essentials-woths");
        shopGuiPlus = config.getConfig().getBoolean("Settings.use-shop-gui-plus-woths");

        if (essentials)
            economyUtil.hookIntoEssentials();
        else if (shopGuiPlus)
            economyUtil.hookIntoShopGuiPlus();
    }


    private EconomyUtil economyUtil;
    private boolean essentials;
    private boolean shopGuiPlus;

    @EventHandler
    public void onToggle(PrinterToggleEvent e) {

        if (!this.isOn()) {
            return;
        }


        BeastPlayer player = e.getPlayer();
        if (e.isOn()) {

            boolean showMoneyTask = config.getConfig().getBoolean("Settings.show-money-in-interval", true);
            long showInterval = config.getConfig().getInt("Settings.show-money-interval-in-seconds", 30);

            if (showMoneyTask) {
                printerManager.addShowMoneyTask(player, showInterval);
            }

            printerManager.enablePrinterForPlayer(player);

        } else {
            this.printerManager.disablePrinterForPlayer(player);
            this.printerManager.removeShowMoneyTask(player);
            if (config.getConfig().getBoolean("Settings.no-fall-damage-on-printer-disable")) {
                this.printerManager.addPlayerToNoFallDamage(player);
            }
        }
    }


    @EventHandler
    public void onPlayerFall(EntityDamageEvent e) {
        if (!isOn() || !(e.getEntity() instanceof Player)) return;
        BeastPlayer player = this.getPlayer((Player) e.getEntity());
        if (!this.printerManager.hasPlayerFallDamageDisabled(player)) return;
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        e.setCancelled(true);

    }


    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (!isOn() || e.isCancelled()) return;
        BeastPlayer player = this.getPlayer((Player) e.getPlayer());
        if (!isOnPrinter(player)) return;
        e.setCancelled(true);
        player.sms(config.getConfig().getString("Settings.Messages.you-cant-open-inventories"));

    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!isOn() || e.isCancelled()) return;
        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!isOnPrinter(player)) return;
        int radius = config.getConfig().getInt("Settings.nearby-enemy-radius");

        //nearby enemies
        if (isFactionsHooked() && player.thereAreNearbyEnemies(radius)) {
            player.performCommand("printer off");
            player.sms(config.getConfig().getString("Settings.Messages.nearby-enemy"));
            return;
        }

        //moved out his land
        if (isFactionsHooked() && config.getConfig().getBoolean("Settings.allow-printer-only-in-own-land") && !player.isAtHisFactionsLand()) {
            player.performCommand("printer off");
            player.sms(config.getConfig().getString("command.Messages.only-in-your-faction-land"));
        }


    }


    @EventHandler
    public void onClick(PlayerDropItemEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOn() || !isOnPrinter(player)) return;

        e.setCancelled(true);
        player.getBukkitPlayer().closeInventory();
        player.sms(config.getConfig().getString("Settings.Messages.cant-drop-items"));
    }


    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!isOn() || !isOnPrinter(player)) return;
        player.performCommand("printer off");
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOn() || e.isCancelled() || !isOnPrinter(player)) return;


        if (config.getConfig().getBoolean("Settings.allow-teleport")) {
            return;
        }

        e.setCancelled(true);
        player.sms( config.getConfig().getString("Settings.Messages.cant-teleport-in-printer-mode"));
    }


    private boolean allowPlayersBreakPlacedBlocksInPrinter() {
        return config.getConfig().getBoolean("Settings.allow-players-remove-blocks-placed-by-them-in-printer");
    }

    private boolean hasPlacedBlock(BeastPlayer player, Block block) {
        return this.printerManager.hasPlayerPlacedBlockWhileInPrinter(player, block);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        BeastPlayer player = this.getPlayer(e.getPlayer());
        Block block = e.getBlock();

        if (!isOn() || e.isCancelled() || !isOnPrinter(player)) return;


        //he can break this block
        if (this.allowPlayersBreakPlacedBlocksInPrinter() && hasPlacedBlock(player, block)) {
            this.printerManager.playerBrokeBlock(player, block);
            return;
        }

        e.setCancelled(true);
        player.sms(config.getConfig().getString("Settings.Messages.you-cant-break-in-printer-mode"));
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (!isOn()) return;

        BeastPlayer player = this.getPlayer(event.getEntity());

        if (!isOnPrinter(player))
            return;

        event.setDroppedExp(0);
        event.getDrops().clear();

        player.performCommand("printer off");
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!isOn() || e.isCancelled() || !isOnPrinter(player)) return;

        Block block = e.getBlockPlaced();
        Material material = block.getType();
        ItemStack itemStack = new ItemStack(material);



        //remove nbt metadata always
        BlockState blockState = block.getState();

        if(this.removeNBT()) {
            this.nbtManager.removeNBTMetadata(blockState);
        }


        double price = 0;

        //blacklisted blocks
        if (config.getConfig().getStringList("Settings.blacklisted-blocks").contains(material.name())) {
            player.sms(config.getConfig().getString("Settings.Messages.cant-place-that-block"));
            e.setCancelled(true);
            return;
        }

        //use ess
        if (essentials) {
            price = economyUtil.getItemPriceEssentials(itemStack);
        }

        //use sgp
        if (shopGuiPlus) {
            price = economyUtil.getPriceBuyShopGuiPlus(itemStack, player.getBukkitPlayer());
        }

        if (shopGuiPlus && price <= 0 && config.getConfig().getBoolean("Settings.use-default-sell-system-if-others-fail")) {
            price = economyUtil.getItemPriceDefault(itemStack);
        }

        if (!essentials && !shopGuiPlus)
            price = economyUtil.getItemPriceDefault(itemStack);

        if (!economyUtil.hasEnoughtMoney(player.getBukkitPlayer(), price)) {
            player.performCommand("printer off");
            player.sms(config.getConfig().getString("Settings.Messages.you-dont-have-money"));
            e.setCancelled(true);
            return;
        }

        if (price <= 0) {
            e.setCancelled(true);
            //send message not allowed block
            player.sms(config.getConfig().getString("Settings.Messages.block-is-not-in-shop"));
            return;
        }


        economyUtil.takeMoney(player.getBukkitPlayer(), price);

        this.printerManager.spentMoneyInPrinter(player, (int) price);
        this.printerManager.playerPlacedBlock(player, block);


        //replace only inventory holder blocks
        if (this.checkIfMustBeReplaced(block)) {
            e.setCancelled(true);
            doTheReplace(e.getBlock());
        }
    }


    private boolean checkIfMustBeReplaced(Block block) {
        return block.getState() instanceof InventoryHolder
                && !block.getType().equals(Material.DISPENSER)
                && !block.getType().equals(Material.DROPPER);
    }

    private void doTheReplace(Block block) {
        //replace block to prevent duping
        Location location = block.getLocation();
        Material mat = block.getType();

        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> {
            Block bAt = location.getWorld().getBlockAt(location);
            bAt.setType(mat);
        }, 1L);
    }



    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        BeastPlayer player = this.getPlayer(e.getPlayer());


        if (!isOn() || !isOnPrinter(player)) {
            return;
        }

        if (e.getMessage().toLowerCase().startsWith("/printer")) {
            return;
        }

        boolean commands = config.getConfig().getBoolean("Settings.Blocked-All-Commands");
        List<String> blockedCommands = config.getConfig().getStringList("Settings.Blocked-Commands");
        List<String> allowedCommands = config.getConfig().getStringList("Settings.Whitelisted-Commands");

        boolean blacklist = config.getConfig().getBoolean("Settings.Black-List-Mode");
        String message = config.getConfig().getString("Settings.Messages.blocked-command");

        if (commands) {
            e.setCancelled(true);
            player.sms(message);
            return;
        }

        if (blacklist) {

            //check all blocked commands
            for (String command : blockedCommands) {
                if (e.getMessage().replace("/", "").startsWith(command)) {
                    e.setCancelled(true);
                    player.sms(message);
                    return;
                }
            }
        } else {


            //check all allowed commands
            for (String command : allowedCommands) {
                if (e.getMessage().replace("/", "").startsWith(command)) {
                    return;
                }
            }

            //not allowed if we are here
            e.setCancelled(true);
            player.sms(message);
        }
    }


    private boolean denyItemsWithInventoryInside() {
        return this.config.getConfig().getBoolean("Settings.disable-blocks-with-inventory-inside");
    }

    private boolean removeNBT(){
        return this.config.getConfig().getBoolean("Settings.remove-nbt-tags");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!isOn() || !isOnPrinter(player)) return;

        String blockedMessage = config.getConfig().getString("Settings.Messages.blocked-interaction");

        ItemStack itemStack = e.getPlayer().getItemInHand();

        //remove nbt metadata from item
        if(this.removeNBT()) {
            if (nbtManager.itemHasNBT(itemStack)) {
                itemStack = nbtManager.removeNBTData(itemStack);
                player.setItemInHand(null);
            }
        }

        //is exp bottle
        if (BeastCore.getInstance().getApi().isExpBottle(itemStack)) {
            player.sms(blockedMessage);
            e.setCancelled(true);
            return;
        }


        //the item can have an inventory
        if (itemStack.getItemMeta() instanceof BlockStateMeta && this.denyItemsWithInventoryInside()) {
            player.sms(blockedMessage);
            e.setCancelled(true);
            return;
        }


        for (String blocked : config.getConfig().getStringList("Settings.interact-blacklisted-items-in-hand")) {
            if (blocked.equalsIgnoreCase(itemStack.getType().toString())) {
                e.setCancelled(true);
                player.sms(config.getConfig().getString("Settings.Messages.blocked-interaction"));
                return;
            }
        }

        Action action = e.getAction();


        if (!action.equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block b = e.getClickedBlock();

        for (String mat : config.getConfig().getStringList("Settings.interact-blacklisted-blocks")) {
            if (b.getType().toString().equalsIgnoreCase(mat)) {
                e.setCancelled(true);
                player.sms(blockedMessage);
                return;
            }
        }
    }


    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!isOn() || !isOnPrinter(player)) return;

        Entity entity = e.getRightClicked();

        for (String mat : config.getConfig().getStringList("Settings.interact-blacklisted-entities")) {
            if (entity.getType().toString().equalsIgnoreCase(mat)) {
                e.setCancelled(true);
                player.sms(config.getConfig().getString("Settings.Messages.blocked-interaction"));
                return;
            }
        }

    }

    @EventHandler
    public void armorStandInteract(PlayerInteractAtEntityEvent event) {

        if (Bukkit.getVersion().contains("1.7.")) return;

        BeastPlayer player = this.getPlayer(event.getPlayer());
        if (!isOn() || !isOnPrinter(player)) return;

        event.setCancelled(true);
        player.sms(config.getConfig().getString("Settings.Messages.blocked-interaction"));
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUp(PlayerPickupItemEvent event) {

        if (!isOn()) return;

        if (event.isCancelled()) return;

        BeastPlayer player = this.getPlayer(event.getPlayer());

        if (!isOnPrinter(player)) return;

        if (config.getConfig().getBoolean("Settings.disable-item-pickup")) {
            event.setCancelled(true);
        }
    }


    public boolean isOnPrinter(BeastPlayer player) {
        return this.printerManager.isPlayerInPrinter(player);
    }


    @EventHandler
    public void onShowMoney(ShowMoneySpentInPrinterEvent event) {

        BeastPlayer player = event.getPlayer();
        int spent = this.printerManager.getSpentInPrinterByPlayer(player);

        String message = config.getConfig().getString("command.Messages.spent-using-printer");
        message = StrUtils.replacePlaceholder(message, "{money}", String.valueOf(spent));

        player.sms(message);
    }

}
