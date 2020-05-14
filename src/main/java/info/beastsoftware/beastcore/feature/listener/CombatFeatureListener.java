package info.beastsoftware.beastcore.feature.listener;

import com.google.common.collect.ImmutableList;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.ICombatNPC;
import info.beastsoftware.beastcore.event.combat.*;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.CombatBlockPlaceGui;
import info.beastsoftware.beastcore.gui.CombatGui;
import info.beastsoftware.beastcore.gui.ISimpleGui;
import info.beastsoftware.beastcore.manager.ICombatCooldownManager;
import info.beastsoftware.beastcore.manager.ICombatNPCManager;
import info.beastsoftware.beastcore.manager.IPvPManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CombatFeatureListener extends AbstractFeatureListener {
    private final List<String> gotKickedPlayers;
    private final ISimpleGui gui;
    private final CombatBlockPlaceGui combatBlockPlaceGui;
    private final ICombatNPCManager combatNPCManager;
    private final IPvPManager pvPManager;
    private final ConcurrentMap<UUID, Set<Location>> previousUpdates = new ConcurrentHashMap<>();
    private final List<UUID> combatPussiesThatDied = new ArrayList<>();
    private static final List<BlockFace> ALL_DIRECTIONS = ImmutableList.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    private final ICombatCooldownManager combatCooldownManager;


    public CombatFeatureListener(IConfig config, ICombatNPCManager combatNPCManager, IPvPManager pvPManager, ICombatCooldownManager combatCooldownManager) {
        super(config, FeatureType.COMBAT);
        this.combatNPCManager = combatNPCManager;
        this.pvPManager = pvPManager;
        this.combatCooldownManager = combatCooldownManager;
        gotKickedPlayers = new ArrayList<>();
        this.combatBlockPlaceGui = new CombatBlockPlaceGui(config);
        this.gui = new CombatGui(config, combatBlockPlaceGui);
    }


    public void restartCooldown(BeastPlayer player) {
        this.combatCooldownManager.restartCooldown(player);
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {

        if (e.isCancelled() || !isOn()) return;

        boolean mobPvP = config.getConfig().getBoolean("Combat-Tag.trigger-combat-with-mobs");
        boolean damagerIsPlayer = e.getDamager() instanceof Player;
        boolean damagedIsPlayer = e.getEntity() instanceof Player;
        boolean damagerIsMob = e.getDamager() instanceof Monster;
        boolean damagedIsMob = e.getEntity() instanceof Monster;
        //////////// MOB COMBAT ////////////////////
        if (mobPvP && (damagedIsMob || damagerIsMob)) {

            //check if player attacks mob
            if (damagedIsMob) {
                if (damagerIsPlayer)
                    tag(this.getPlayer((Player) e.getDamager()));
            }



            //check if mob attacks player
            if (damagerIsMob) {
                if (damagedIsPlayer)
                    tag(this.getPlayer((Player) e.getEntity()));
            }

            return;
        }


        /////////// NORMAL COMBAT //////////////////

        //if damaged is not a player return
        if (!damagedIsPlayer) return;

        BeastPlayer player = this.getPlayer((Player) e.getEntity());

        //tag players on direct pvp
        if (damagerIsPlayer) {

            BeastPlayer damager = this.getPlayer((Player) e.getDamager());
            //same faction
            if (isFactionsHooked() && player.hasFaction() && damager.hasFaction()) {
                if (player.getMyFaction().equals(damager.getMyFaction())) {
                    return;
                }

                if (player.isAlly(damager)) return;
            }

            tag(player, damager);
        }


        // tag player on enderpearl hitting
        if (isPearlTag(e)) {
            if (!Bukkit.getVersion().contains("1.7.")) {
                if (isOnCombat(player))
                    tag(player);
            }
        }

        //bow attack
        if (isBow(e)) {
            Arrow arrow = (Arrow) e.getDamager();
            BeastPlayer shooter = this.getPlayer((Player) arrow.getShooter());
            //same faction
            if (this.isFactionsHooked() && player.hasFaction() && shooter.hasFaction()) {
                if (player.getMyFaction().equals(shooter.getMyFaction())) return;
            }
            tag(player, shooter);
        }
    }

    private boolean isPearlTag(EntityDamageByEntityEvent e) {
        return ((e.getDamager() instanceof EnderPearl || e.getDamager() instanceof Egg || e.getDamager() instanceof Snowball || e.getDamager() instanceof FishHook));

    }

    private boolean isBow(EntityDamageByEntityEvent e) {
        return e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player;

    }

    private void tag(BeastPlayer player, BeastPlayer damager) {
        tag(player);
        tag(damager);
    }

    private void tag(BeastPlayer player) {

        //start combat
        if (!isOnCombat(player))
            Bukkit.getPluginManager().callEvent(new CombatStartEvent(player));

            //restart combat
        else
            Bukkit.getPluginManager().callEvent(new CombatRestartEvent(player));

    }


    private boolean hasBypassPermission(BeastPlayer player) {
        return player.hasPermission(Objects.requireNonNull(config.getConfig().getString("Combat-Tag.bypass-permission")));
    }


    private int getCombatTime() {
        return config.getConfig().getInt("Combat-Tag.duration");
    }


    private boolean isCancellingFlight() {
        return config.getConfig().getBoolean("Combat-Tag.cancel-fly");
    }


    private void cancelFlight(BeastPlayer player) {
        player.getBukkitPlayer().setAllowFlight(false);
        player.getBukkitPlayer().setFlying(false);
    }


    private void sendMessageInCombat(BeastPlayer player) {
        String message = config.getConfig().getString("Combat-Tag.combat-started");
        player.sms(message);
    }


    private void addCooldown(BeastPlayer player) {
        int combatTime = getCombatTime();
        this.combatCooldownManager.addCooldown(player, combatTime);
    }


    /**
     * Puts a player in combat mode, disables flight if necessary
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCombatStart(CombatStartEvent event) {

        //disabled or cancelled
        if (!isOn() || event.isCancelled()) return;

        BeastPlayer player = event.getTarget();

        //can bypass combat
        if (hasBypassPermission(player)) return;


        boolean isCancellingFlight = isCancellingFlight();


        //add cooldown
        addCooldown(player);

        //cancel flight
        if (isCancellingFlight) {
            cancelFlight(player);
        }

        //send message to player
        sendMessageInCombat(player);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCombatRestart(CombatRestartEvent event) {

        if (!isOn() || event.isCancelled()) return;

        BeastPlayer player = event.getTarget();

        if (hasBypassPermission(player)) return;

        restartCooldown(player);
    }


    @EventHandler
    public void onPlayerGotkicked(PlayerKickEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOn()) return;
        if (!isOnCombat(player)) return;
        gotKickedPlayers.add(e.getPlayer().getName());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogOut(PlayerQuitEvent e) {
        if (!isOn()) return;
        final BeastPlayer player = this.getPlayer(e.getPlayer());
        //check if player got kicked, true = not die
        if (gotKickedPlayers.contains(player.getName())) {
            gotKickedPlayers.remove(player.getName());
            return;
        }

        //he is a double pussy just kill him
        if (this.combatNPCManager.isAPussy(player.getOfflinePlayer())) {
            ICombatNPC combatNPC = this.combatNPCManager.getNPCOfPlayer(player.getOfflinePlayer());
            Bukkit.getPluginManager().callEvent(new CombatPussyMustDieEvent(combatNPC));
            return;
        }


        //kill player in case he disconnected
        if (isOnCombat(player)) {
            Bukkit.getPluginManager().callEvent(new CombatPussyDetectedEvent(player));
        }
    }


    //on command denied
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeniedCommand(PlayerCommandPreprocessEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOn() || !isOnCombat(player)) return;

        String command = e.getMessage();

        //is combat command, allow
        if (isCombatCommand(command)) {
            return;
        }

        //blacklisted commands
        if (isOnBlackList() && commandListed(e.getMessage(), config.getConfig().getStringList("Combat-Tag.blacklisted-commands"))) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Combat-Tag.denied-command"));
            return;
        }


        List<String> whitelist = this.getWhitelist();

        //whitelisted commands
        if (isOnWhiteList() && !commandListed(e.getMessage(), whitelist)) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Combat-Tag.denied-command"));
        }

    }

    private boolean isCombatCommand(String command) {
        return command.startsWith("/ct") || command.startsWith("/combat");
    }

    private boolean isOnWhiteList() {
        return config.getConfig().getBoolean("Combat-Tag.whitelist-mode");
    }

    private boolean isOnBlackList() {
        return config.getConfig().getBoolean("Combat-Tag.blacklist-mode");
    }

    private boolean commandListed(String command, List<String> commands) {
        String s = command.toLowerCase();
        for (String listed : commands) {
            String listedCase = listed.toLowerCase();
            if (s.equals(listedCase))
                return true;

            if (listedCase.contains("*") && s.startsWith(listedCase))
                return true;
        }

        return false;
    }


    private List<String> getWhitelist() {
        return config.getConfig().getStringList("Combat-Tag.whithelisted-commands");
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {

        BeastPlayer player = this.getPlayer(e.getEntity());

        if (isOn() && isOnCombat(player)) {
            this.combatCooldownManager.endCooldown(player);
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!isOn() || e.isCancelled()) return;
        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOnCombat(player)) return;

        Action action = e.getAction();

        //send barrier updates
        this.sendBlockChanges(player);

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;


        //enderpearls disabled
        if (areEnderpearlsDisabled() && isEnderpearl(player.getItemInHand())) {
            e.setCancelled(true);
            sendMessageEnderpearlsDisabled(player);
            return;
        }


        //other throw events
        if (shouldRestartTag(player.getItemInHand())) {
            this.restartCooldown(player);
        }

    }


    private boolean shouldRestartTag(ItemStack item) {
        ///////////// 1.13
        Material material;
        try {
            material = Material.valueOf("SNOW_BALL");
        } catch (IllegalArgumentException ignored) {
            material = Material.SNOWBALL;
        }
        ///////////// 1.13


        return item.getType().equals(material) || item.getType().equals(Material.EGG)
                || item.getType().equals(Material.FISHING_ROD);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        if (!isOn() || !isOnCombat(this.getPlayer(e.getPlayer()))) return;
        BeastPlayer player = this.getPlayer(e.getPlayer());


        //cause of the teleport is enderpearl
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {

            //enderpearls not allowed cancel event
            if (areEnderpearlsDisabled()) {
                e.setCancelled(true);
                sendMessageEnderpearlsDisabled(player);
                return;
            }

            //reset combat tag on teleport
            if (config.getConfig().getBoolean("Combat-Tag.reset-on-enderpearl"))
                restartCooldown(player);

            return;
        }


        //if we are here, and teleport in combat is disabled, cancel event
        if (isTeleportDisabled()) {
            sendMessageNoTeleport(player);
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!isOn() || !isOnCombat(player)) return;

        Block block = e.getBlockPlaced();

        //cant place that block tho
        if (combatBlockPlaceGui.isBlockedBlock(block)) {
            e.setCancelled(true);
            player.sms(config.getConfig().getString("Combat-Tag.Blocked-Item-Message"));
        }

    }


    private boolean isTeleportDisabled() {
        String path = "Combat-Tag.disable-teleport";
        return config.getConfig().getBoolean(path);
    }


    private boolean areEnderpearlsDisabled() {
        String path = "Combat-Tag.allow-enderpearls-in-combat";
        return !config.getConfig().getBoolean(path);
    }


    private void sendMessageEnderpearlsDisabled(BeastPlayer player) {
        String path = "Combat-Tag.enderpearls-not-allowed-in-combat-message";
        String message = config.getConfig().getString(path);
        player.sms(message);
    }


    private boolean isEnderpearl(ItemStack itemStack) {
        return itemStack.getType().equals(Material.ENDER_PEARL);
    }


    private void sendMessageNoTeleport(BeastPlayer player) {
        String path = "Combat-Tag.no-teleport-in-combat";
        String message = config.getConfig().getString(path);
        player.sms(message);
    }

    public int getTimeInCOmbat(BeastPlayer player) {
        try {
            return this.combatCooldownManager.getCombatCooldownOfPlayer(player).getTimeLeft();
        } catch (Exception ignored) {
            return 0;
        }
    }


    public boolean isOnCombat(BeastPlayer player) {
        return this.combatCooldownManager.isOnCooldown(player);
    }

    public String getCombatFormatted(BeastPlayer player) {
        return "";
    }

    public void setPlayerInCombat(BeastPlayer player, int time) {
        this.combatCooldownManager.addCooldown(player, time);
    }

    public void endCombatForPlayer(BeastPlayer player) {
        this.combatCooldownManager.endCooldown(player);
    }


    public void removePlayerFromCombatManager(BeastPlayer player) {
        this.combatCooldownManager.removeCooldownOfPlayer(player);
    }


    @EventHandler
    public void onCombatEndEvent(CombatEndEvent endEvent) {
        BeastPlayer player = endEvent.getTarget();

        //remove the task
        this.removePlayerFromCombatManager(player);

        //remove all changed blocks for the player
        sendUpdateToRestoreBlocks(player, previousUpdates.get(player.getUuid()));
        previousUpdates.remove(player.getUuid());

        //send message
        String message = config.getConfig().getString("Combat-Tag.no-longer-in-combat");
        player.sms(message);

    }


    @EventHandler
    public void onCombatLog(CombatPussyDetectedEvent event) {
        if (!isOn()) return;

        BeastPlayer player = event.getTarget();
        int time = config.getConfig().getInt("Combat-Tag.time-to-die-after-disconnecting");

        this.combatNPCManager.addPussy(player, time);
    }


    @EventHandler
    public void onCombatPussyTimeout(CombatPussyMustDieEvent event) {

        ICombatNPC combatNPC = event.getNpc();
        OfflinePlayer offlinePlayer = combatNPC.getOfflinePlayer();


        //kill npc and drop inventory
        this.combatPussiesThatDied.add(combatNPC.getPlayerUUID());
        combatNPC.dropContents();

        //remove npc
        combatNPC.despawn();
        this.combatNPCManager.removeNPC(combatNPC);

        //broadcast pussy
        this.broadcastMessageIsAPussy(offlinePlayer);
    }


    @EventHandler
    private void onPussyJoined(PlayerJoinEvent event) {

        if (!isOn()) return;

        BeastPlayer player = this.getPlayer(event.getPlayer());
        UUID uuid = player.getUuid();

        //he stills being a pussy, lets give him 10 s of grace
        if (this.combatNPCManager.isAPussy(player.getOfflinePlayer())) {

            //restart combat tag
            addCooldown(player);

            //despawn npc
            ICombatNPC combatNPC = this.combatNPCManager.getNPCOfPlayer(player.getOfflinePlayer());
            combatNPC.despawn();

            //freeze countdown
            combatNPC.freezeTime();

            //he will stop being a pussy 10 s later
            Bukkit
                    .getScheduler()
                    .runTaskLater(BeastCore.getInstance(),
                            () -> this.combatNPCManager.removeNPC(combatNPC), 10 * 20L);

        }


        //is not a pussy
        if (!combatPussiesThatDied.contains(uuid)) return;

        //is a pussy lets kill him
        //clear inventory
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        //kill him
        player.getBukkitPlayer().setHealth(0);

        combatPussiesThatDied.remove(uuid);
    }


    private void broadcastMessageIsAPussy(OfflinePlayer player) {

        String name = player.getName();

        String message = config.getConfig().getString("Combat-Tag.combat-logger-detected");

        message = StrUtils.replacePlaceholder(message, "{player}", name);

        Bukkit.broadcastMessage(StrUtils.translate(message));
    }


    @EventHandler
    public void onNPCAttack(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (this.combatNPCManager.isNPC(entity)) {
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerGotNearToNoPvPZone(PlayerMoveEvent event) {

        //disabled
        if (!isOn()) return;

        BeastPlayer player = this.getPlayer(event.getPlayer());

        //not in combat
        if (!isOnCombat(player)) return;

        Location to = event.getTo();
        Location from = event.getFrom();

        //he has not moved
        if (ILocationUtil.isSameLocation(to, from)) return;

        //send barriers
        this.sendBlockChanges(player);

    }


    private void sendBlockChanges(BeastPlayer player) {

        boolean denyAccess = config.getConfig().getBoolean("Combat-Tag.deny-access-to-no-pvp-zones");
        if (!denyAccess) return;


        // Asynchronously send block changes around player
        Bukkit.getScheduler().runTaskAsynchronously(BeastCore.getInstance(), () -> {

            UUID uuid = player.getUuid();

            // Update the players force field perspective and find all blocks to stop spoofing
            Set<Location> changedBlocks = getChangedBlocks(player);
            Material forceFieldMaterial = Material.GLASS;
            int forceFieldByte = 0;

            try {
                forceFieldMaterial = Material.valueOf(config.getConfig().getString("Combat-Tag.barrier-material-for-no-pvp-zones"));
                forceFieldByte = config.getConfig().getInt("Combat-Tag.barrier-damage-for-no-pvp-zones");
            } catch (Exception ignored) {
                Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &cTHE MATERIAL OF THE COMBAT BARRIERS FOR NO-PVP ZONES IS INVALID!!"));
            }

            Set<Location> removeBlocks;
            if (previousUpdates.containsKey(uuid)) {
                removeBlocks = previousUpdates.get(uuid);
            } else {
                removeBlocks = new HashSet<>();
            }

            //Update with barriers
            sendUpdateToDrawBarrier(player, forceFieldMaterial, (byte) forceFieldByte, changedBlocks);
            removeBlocks.removeAll(changedBlocks);

            // Remove no longer used spoofed blocks
            sendUpdateToRestoreBlocks(player, removeBlocks);

            previousUpdates.put(uuid, changedBlocks);
        });
    }


    private void sendUpdateToRestoreBlocks(BeastPlayer player, Set<Location> removeBlocks) {

        if (Objects.nonNull(removeBlocks) && !removeBlocks.isEmpty()) {

            removeBlocks
                    .stream()
                    .map(Location::getBlock)
                    .forEach(b -> player.getBukkitPlayer().sendBlockChange(b.getLocation(), b.getType(), b.getData()));
        }
    }


    private void sendUpdateToDrawBarrier(BeastPlayer player, Material material, int damage, Set<Location> changedBlocks) {
        changedBlocks
                .forEach(l -> player.getBukkitPlayer().sendBlockChange(l, material, (byte) damage));
    }


    private Set<Location> getChangedBlocks(BeastPlayer player) {
        Set<Location> locations = new HashSet<>();

        // Do nothing if player is not tagged
        if (!isOnCombat(player)) return locations;

        // Find the radius around the player
        int r = 10;
        Location l = player.getLocation().getBukkitLocation();
        Location loc1 = l.clone().add(r, 0, r);
        Location loc2 = l.clone().subtract(r, 0, r);
        int topBlockX = loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
        int bottomBlockX = loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX();
        int topBlockZ = loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();
        int bottomBlockZ = loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ();

        // Iterate through all blocks surrounding the player
        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                // Location corresponding to current loop
                Location location = new Location(l.getWorld(), (double) x, l.getY(), (double) z);

                // PvP is enabled here, no need to do anything else
                if (pvPManager.isPvPEnabledInLocation(location)) continue;

                // Check if PvP is enabled in a location surrounding this
                if (!isPvpSurrounding(location)) continue;

                for (int i = -r; i < r; i++) {
                    Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

                    loc.setY(loc.getY() + i);

                    // Do nothing if the block at the location is not air
                    if (!loc.getBlock().getType().equals(Material.AIR)) continue;

                    // Add this location to locations
                    locations.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                }
            }
        }

        return locations;
    }


    private boolean isPvpSurrounding(Location loc) {
        for (BlockFace direction : ALL_DIRECTIONS) {
            if (pvPManager.isPvPEnabledInLocation(loc.getBlock().getRelative(direction).getLocation())) {
                return true;
            }
        }

        return false;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {


        if (event.isCancelled()) {
            return;
        }


        BeastPlayer player = this.getPlayer(event.getPlayer());

        if (isOnCombat(player)) {
            event.setCancelled(true);
        }

    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTrade(PlayerInteractAtEntityEvent event) {

        if (event.isCancelled()) {
            return;
        }

        Entity entity = event.getRightClicked();

        if (entity instanceof Villager) {

            //cancel trade
            if (combatNPCManager.isNPC(entity)) {
                event.setCancelled(true);
            }

        }


    }

    private boolean inventoriesDisabled(){
        return config.getConfig().getBoolean("Combat-Tag.disable-inventories");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpen(InventoryOpenEvent event){

        Player player = (Player) event.getPlayer();

        BeastPlayer beastPlayer = this.getPlayer(player);

        if(isOnCombat(beastPlayer)){

            Inventory inventory = event.getInventory();

            if(!inventory.equals(player.getInventory()) && this.inventoriesDisabled()){
                String message = this.config.getConfig().getString("Combat-Tag.inventories-disabled");
                beastPlayer.sms(message);
                event.setCancelled(true);
            }
        }
    }
}
