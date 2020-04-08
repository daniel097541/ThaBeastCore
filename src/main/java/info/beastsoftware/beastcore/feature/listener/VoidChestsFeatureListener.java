package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.IHologramUtil;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.VoidChestBroadcastEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.IEconomyHook;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import info.beastsoftware.hookcore.entity.BeastLocation;
import info.beastsoftware.hookcore.entity.BeastLocationImpl;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class VoidChestsFeatureListener extends AbstractFeatureListener {


    private int sellTask;
    private int lifeTimeTask;
    private EconomyUtil economyUtil;
    private final HashMap<String, HashMap<Integer, BeastLocation>> chestHash = new HashMap<>();
    private final HashMap<UUID, Double> soldByPlayer = new HashMap<>();

    public VoidChestsFeatureListener(IConfig config, IDataConfig dataConfig, IEconomyHook economyHook) {
        super(config, dataConfig, FeatureType.VOID_CHESTS);
        //create chest hashmap and load chest from configs

        if (economyHook == null) {
            return;
        }


        loadChests();
        economyUtil = new EconomyUtil(economyHook.getEconomy());


        //check if hook is enabled
        if (config.getConfig().getBoolean("Void-Chests.Holograms.enabled")) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("HolographicDisplays");
            if (plugin == null)
                disableHook();
        }

        if (config.getConfig().getBoolean("Void-Chests.use-essentials-worth"))
            economyUtil.hookIntoEssentials();

        if (config.getConfig().getBoolean("Void-Chests.use-shop-gui-plus-worth"))
            economyUtil.hookIntoShopGuiPlus();

        startSellTask(config.getConfig().getInt("Void-Chests.Settings.sell-time"));
        startLifeTimeTask();

        startBroadcastTask();
    }


    private void startBroadcastTask() {
        int timeToBroadcast = config.getConfig().getInt("Void-Chests.broadcast-sold-interval");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BeastCore.getInstance(), () ->
                Bukkit.getPluginManager().callEvent(new VoidChestBroadcastEvent()), 0L, timeToBroadcast * 20L);
    }

    private void disableHook() {
        config.getConfig().set("Void-Chests.Holograms.enabled", false);
        Bukkit.getLogger().info("Holographic Displays is not installed, autodisabling hook!");
        config.save();
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {

        if (e.isCancelled()) return;

        Block block = e.getBlock();

        //not a chest
        if (!block.getType().equals(Material.CHEST)) return;


        //not a void chest
        if (!isVoidChestLocation(block.getLocation())) return;


        //if player dont have break permission
        if (!e.getPlayer().hasPermission(config.getConfig().getString("Void-Chests.Settings.break-permission"))) {
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Void-Chests.Settings.no-break-permission-msg"));
            return;
        }

        //remove chest from list
        removeVoidChest(block.getLocation(), true);

        String name = config.getConfig().getString("Void-Chests.Item.name");
        List<String> lore = stringUtil.translateLore(config.getConfig().getStringList("Void-Chests.Item.lore"));
        Material material = Material.CHEST;

        ItemStack voidChest = IInventoryUtil.createItem(1, material, name, lore, true);

        //cancel event and drop chest
        e.setCancelled(true);
        block.setType(Material.AIR);
        block.getWorld().dropItem(block.getLocation(), voidChest);
    }

    //look for the void chest location
    private boolean isVoidChestLocation(Location location) {

        BeastLocation locAt = new BeastLocationImpl(location);

        for (String playerName : chestHash.keySet()) {
            for (BeastLocation location1 : chestHash.get(playerName).values()) {
                if (locAt.equals(location1)) return true;
            }
        }
        return false;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (!e.getPlayer().isSneaking()) return;

        Block block = e.getClickedBlock();

        Location location = block.getLocation();

        BeastLocation locAt = new BeastLocationImpl(location);

        if (!isVoidChestLocation(location)) return;

        if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) return;

        String name = "";

        for (String nm : chestHash.keySet()) {
            for (int id : chestHash.get(nm).keySet()) {
                if (locAt.equals(chestHash.get(nm).get(id))) {
                    name = nm;
                    break;
                }
            }
        }

        Player player = e.getPlayer();

        String message = config.getConfig().getString("Void-Chests.Interact-Messages." + "get-chest-owners-name-message");

        message = stringUtil.replacePlaceholder(message, "{player}", name);

        BeastCore.getInstance().sms(player, message);

        e.setCancelled(true);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        Block block = e.getBlock();

        if (e.isCancelled() || !block.getType().equals(Material.CHEST)) return;

        Player player = e.getPlayer();

        if (!isVoidChest(e.getPlayer().getItemInHand())) return;

        //if disabled, dont allow players place chests
        if (!isOn()) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Void-Chests.disabled-message"));
            e.setCancelled(true);
            return;
        }

        //if player dont have place place permission
        if (!player.hasPermission(config.getConfig().getString("Void-Chests.Settings.place-permission"))) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Void-Chests.Settings.no-place-permission-msg"));
            return;
        }

        //add the void chest to the locations hash and the config files
        addVoidChest(player.getName(), block.getLocation());


    }


    private void removeVoidChest(Location location, boolean b) {
        String player = null;
        int chestId = 0;
        boolean found = false;
        BeastLocation locAt = new BeastLocationImpl(location);

        for (String playerName : chestHash.keySet()) {
            for (int id : chestHash.get(playerName).keySet()) {
                if (found) break;
                if (locAt.equals(chestHash.get(playerName).get(id))) {
                    chestId = id;
                    player = playerName;
                    found = true;
                    break;
                }
            }
        }

        if (!found) return;

        //remove chest from hash
        if (b)
            chestHash.get(player).remove(chestId);

        //remove chest from config files and save
        removeFromConfigFiles(chestId, player);

        //remove hologram
        if (config.getConfig().getBoolean("Void-Chests.Holograms.enabled"))
            removeHologram(player, chestId);
    }


    private void removeHologram(String name, int id) {
        IHologramUtil.removeHologram(name, id);
    }


    private void addVoidChest(String player, Location location) {

        int id = 0;

        //if player has not chests, create the hash and write the chest in configs
        if (!chestHash.containsKey(player)) {
            HashMap<Integer, BeastLocation> hash = new HashMap<>();
            hash.put(id, new BeastLocationImpl(location));
            chestHash.put(player, hash);
            addToConfigFiles(id, player, location);
            //create the hologram over the chest
            if (config.getConfig().getBoolean("Void-Chests.Holograms.enabled"))
                createHologram(location, player, id);
            return;
        }


        //las chest id in players config + 1
        id = getLastId(player) + 1;

        //add the id to the hashmap and to the config files
        chestHash.get(player).put(id, new BeastLocationImpl(location));
        addToConfigFiles(id, player, location);


        //create the hologram over the chest
        if (config.getConfig().getBoolean("Void-Chests.Holograms.enabled"))
            createHologram(location, player, id);
    }


    private void createHologram(Location location, String player, int id) {
        Location newLocation = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        newLocation.setY(location.getBlockY() + 2);
        newLocation.setZ(location.getBlockZ() + 0.5);
        List<String> lines = config.getConfig().getStringList("Void-Chests.Holograms.hologram-lines");
        List<String> linesTrans = new ArrayList<>();
        for (String line : lines) {
            line = stringUtil.replacePlaceholder(line, "{player}", player);
            linesTrans.add(line);
        }
        linesTrans = stringUtil.translateLore(linesTrans);
        IHologramUtil.createHologram(newLocation, linesTrans, player, id);
    }

    private void removeFromConfigFiles(int id, String player) {
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id, null);
        this.dataConfig.save(player);
    }

    private void addToConfigFiles(int id, String player, Location location) {
        this.dataConfig.loadConfig(player);
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id + ".world", location.getWorld().getName());
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id + ".x", location.getX());
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id + ".y", location.getY());
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id + ".z", location.getZ());
        this.dataConfig.getConfigByName(player).set("Chest-Locations." + id + ".life-time", config.getConfig().getInt("Void-Chests.Life-Time.max-life-time-in-seconds"));

        this.dataConfig.save(player);
    }


    //to get the id of the last chest in the players config
    private int getLastId(String player) {
        int index = this.dataConfig.getConfigByName(player).getConfigurationSection("Chest-Locations").getKeys(false).size() - 1;
        if (index < 0) return 0;
        return Integer.parseInt((String) this.dataConfig.getConfigByName(player).getConfigurationSection("Chest-Locations").getKeys(false).toArray()[index]);
    }


    private void loadChests() {
        for (String playerName : this.dataConfig.getConfigs().keySet()) {
            YamlConfiguration configuration = this.dataConfig.getConfigByName(playerName);
            for (String idString : configuration.getConfigurationSection("Chest-Locations").getKeys(false)) {
                int chestId = Integer.parseInt(idString);
                //get chest info from players config
                String worldName = configuration.getString("Chest-Locations." + idString + ".world");
                int x = configuration.getInt("Chest-Locations." + idString + ".x");
                int y = configuration.getInt("Chest-Locations." + idString + ".y");
                int z = configuration.getInt("Chest-Locations." + idString + ".z");

                World world = Bukkit.getWorld(worldName);

                //world = null means world removed or invalid, ignore this section and continue
                if (world == null) continue;

                if (configuration.get("Chest-Locations." + idString + ".life-time") == null)
                    configuration.set("Chest-Locations." + idString + ".life-time", config.getConfig().getInt("Void-Chests.Life-Time.max-life-time-in-seconds"));

                Location location = new Location(world, x, y, z);

                //if the main hash does not contain the player info, create it
                if (!chestHash.containsKey(playerName)) {
                    HashMap<Integer, BeastLocation> hash = new HashMap<>();
                    chestHash.put(playerName, hash);
                }

                //location added to the hash
                chestHash.get(playerName).put(chestId, new BeastLocationImpl(location));
            }
        }
    }


    private boolean isVoidChest(ItemStack itemStack) {

        if (!itemStack.getType().equals(Material.CHEST)) return false;

        ItemMeta meta = itemStack.getItemMeta();

        String displayName = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Void-Chests.Item.name"));

        if (!Bukkit.getBukkitVersion().contains("1.7"))
            return displayName.equalsIgnoreCase(meta.getDisplayName()) && meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)
                    && meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE);

        return true;
    }

    //task that sells items in void chests iterating over loaded chests
    private void startSellTask(int duration) {
        sellTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(BeastCore.getInstance(), () -> {

            //if not on or no chests inside list, do nothing
            if (!isOn() || chestHash.isEmpty()) return;

            //iterate over chest locations
            for (String playerName : chestHash.keySet()) {

                //get player here
                Player player = Bukkit.getPlayer(playerName);
                OfflinePlayer offlinePlayer = null;

                //player offline, do nothing
                if (player == null) {
                    continue;
                }


                //iterate over locations
                for (BeastLocation location : chestHash.get(playerName).values()) {

                    Chest chest;

                    //get chest from stored location
                    try {
                        chest = (Chest) location.getBukkitLocation().getBlock().getState();

                    } catch (ClassCastException e) {
                        continue;
                    }

                    //empty chest, continue
                    if (IInventoryUtil.isEmpty(chest.getBlockInventory()))
                        continue;
                    //sell items for player
                    sellItems(player, offlinePlayer, chest.getBlockInventory());
                }
            }
        }, 0L, duration * 20L);

    }


    private void startLifeTimeTask() {
        boolean removeChest = config.getConfig().getBoolean("Void-Chests.Life-Time.remove-chest");
        lifeTimeTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(BeastCore.getInstance(), () -> {

            if (!config.getConfig().getBoolean("Void-Chests.Life-Time.enabled") || !isOn() || chestHash.isEmpty())
                return;

            for (String name : chestHash.keySet()) {
                for (Iterator<Integer> iterator = chestHash.get(name).keySet().iterator(); iterator.hasNext(); ) {

                    int id = iterator.next();

                    int time = this.dataConfig.getConfigByName(name).getInt("Chest-Locations." + id + ".life-time");

                    if (config.getConfig().getBoolean("Void-Chests.Holograms.enabled")) {

                        //UPDATE LIFE TIME LINE IN THE HOLOGRAM
                        int cont = 0;
                        boolean found = false;
                        for (String line : config.getConfig().getStringList("Void-Chests.Holograms.hologram-lines")) {
                            if (line.contains("{life_time}")) {
                                found = true;
                                break;
                            }
                            cont++;
                        }

                        if (found) {
                            String line = config.getConfig().getStringList("Void-Chests.Holograms.hologram-lines").get(cont);
                            line = stringUtil.replacePlaceholder(line, "{life_time}", "" + stringUtil.formatCooldown(time));
                            IHologramUtil.editHologramLine(name, id, cont, ChatColor.translateAlternateColorCodes('&', line));
                        }

                    }

                    if (time <= 0) {
                        Location location = chestHash.get(name).get(id).getBukkitLocation();

                        //remove void chest from world
                        if (removeChest) {
                            location.getWorld().getBlockAt(location).setType(Material.AIR);
                        }

                        //remove void chest from configs
                        removeVoidChest(location, false);
                        iterator.remove();
                        continue;
                    }

                    time--;
                    this.dataConfig.getConfigByName(name).set("Chest-Locations." + id + ".life-time", time);
                    this.dataConfig.save(name);
                }
            }
        }, 0L, 20L);
    }


    private void sellItems(Player player, OfflinePlayer offlinePlayer, Inventory inventory) {
        boolean essentials = config.getConfig().getBoolean("Void-Chests.use-essentials-worth");
        boolean shopGUIPlus = config.getConfig().getBoolean("Void-Chests.use-shop-gui-plus-worth");
        double total = 0;
        int slot = 0;

        //iterate over all items
        for (ItemStack itemStack : inventory) {

            //item null continue
            if (itemStack == null) {
                slot++;
                continue;
            }

            double price = 0.0;

            //use essentials worth
            if (essentials) {
                price = economyUtil.getItemPriceEssentials(itemStack);
            }


            //use shop gui plus worth
            if (shopGUIPlus)
                price = economyUtil.getItemPriceShopGuiPlus(itemStack, player);


            if (shopGUIPlus && price <= 0.0 && config.getConfig().getBoolean("Void-Chests.use-default-sell-system-if-others-fail")) {
                price = economyUtil.getItemPriceDefault(itemStack);
            }

            //use default worth
            if (!essentials && !shopGUIPlus)
                price = economyUtil.getItemPriceDefault(itemStack);

            //item can be sold, remove the item
            if (price > 0.0) {
                inventory.setItem(slot, null);
            }

            total += price;
            slot++;
        }

        if (player != null) {
            economyUtil.giveMoney(player, total);

            if (total > 0.0) {
                if (!soldByPlayer.containsKey(player.getUniqueId())) {
                    soldByPlayer.put(player.getUniqueId(), 0.0);
                }

                double sold = soldByPlayer.get(player.getUniqueId());
                sold += total;

                soldByPlayer.put(player.getUniqueId(), sold);
            }
        }

        if (offlinePlayer != null)
            economyUtil.giveMoney(offlinePlayer, total);

    }


    @EventHandler
    public void onBroadcast(VoidChestBroadcastEvent event) {

        if (!isOn()) return;

        boolean broadcast = config.getConfig().getBoolean("Void-Chests.broadcast-sold");

        if (!broadcast) return;

        String messageFormat = config.getConfig().getString("Void-Chests.message-sold");

        //broadcast to each online player
        soldByPlayer
                .entrySet()
                .parallelStream()
                //only ones that sold something
                .filter(e -> e.getValue() > 0.0)
                //only online ones
                .filter(entry -> Bukkit.getPlayer(entry.getKey()) != null)
                //broadcast
                .forEach(entry -> {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    double sold = entry.getValue();
                    String message = messageFormat;
                    message = StrUtils.replacePlaceholder(message, "{sold}", String.valueOf(sold));
                    StrUtils.sms(player, message);
                });

        //clear for the next broadcast
        soldByPlayer.clear();

    }


}
