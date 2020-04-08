package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class MobHoppersFeatureListener extends AbstractFeatureListener {


    public MobHoppersFeatureListener(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig, FeatureType.MOB_HOPPERS);
        this.loadLocations();
    }

    private final HashMap<String, HashMap<Integer, Location>> mobHoppersLocations = new HashMap<>();


    private void loadLocations() {

        for (String mob : this.dataConfig.getConfigs().keySet()) {

            YamlConfiguration configuration = this.dataConfig.getConfigByName(mob);

            if (!mobHoppersLocations.containsKey(mob)) {
                HashMap<Integer, Location> hashMap = new HashMap<>();
                mobHoppersLocations.put(mob, hashMap);
            }

            for (String idString : configuration.getConfigurationSection("Locations").getKeys(false)) {

                //get chest info from players config
                String worldName = configuration.getString("Locations." + idString + ".world");
                int x = configuration.getInt("Locations." + idString + ".x");
                int y = configuration.getInt("Locations." + idString + ".y");
                int z = configuration.getInt("Locations." + idString + ".z");

                World world = Bukkit.getWorld(worldName);

                //world = null means world removed or invalid, ignore this section and continue
                if (world == null) continue;

                Location location = new Location(world, x, y, z);

                mobHoppersLocations.get(mob).put(Integer.parseInt(idString), location);
            }
        }
    }

    private boolean isMobHopperLocation(Location location) {

        for (String mob : mobHoppersLocations.keySet()) {
            if (mobHoppersLocations.get(mob).containsValue(location)) {
                return true;
            }
        }

        return false;
    }


    private String getMobHopperType(Location location) {

        for (String mob : mobHoppersLocations.keySet()) {
            if (mobHoppersLocations.get(mob).containsValue(location)) {
                return mob;
            }
        }

        return "nope";
    }


    private int getMobHopperId(Location location, String type) {
        for (int id : mobHoppersLocations.get(type).keySet()) {
            if (mobHoppersLocations.get(type).get(id).equals(location))
                return id;
        }

        return -1;
    }


    private void addMobHopper(Location location, String type) {

        int id = 0;

        if (!mobHoppersLocations.containsKey(type))
            mobHoppersLocations.put(type, new HashMap<>());

        if (!mobHoppersLocations.get(type).isEmpty())
            id = getLastId(type) + 1;

        mobHoppersLocations.get(type).put(id, location);

        this.dataConfig.loadConfig(type);
        this.dataConfig.getConfigByName(type).set("Locations." + id + ".world", location.getWorld().getName());
        this.dataConfig.getConfigByName(type).set("Locations." + id + ".x", location.getX());
        this.dataConfig.getConfigByName(type).set("Locations." + id + ".y", location.getY());
        this.dataConfig.getConfigByName(type).set("Locations." + id + ".z", location.getZ());
        this.dataConfig.save(type);

    }

    private void removeMobHopper(Location location) {
        String type = getMobHopperType(location);

        int id = getMobHopperId(location, type);

        if (id == -1) return;

        mobHoppersLocations.get(type).remove(id);

        this.dataConfig.getConfigByName(type).set("Locations." + id, null);
        this.dataConfig.save(type);
    }


    private int getLastId(String type) {
        int id = 0;

        for (int newId : mobHoppersLocations.get(type).keySet()) {
            if (newId > id) {
                id = newId;
            }
        }

        return id;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!isOn() || (e.getEntity() instanceof Player)) return;

        List<String> mobs = config.getConfig().getStringList("Mob-Hoppers.Settings.mob-list");

        if (!mobs.contains(e.getEntity().getType().toString()) && !mobs.contains("ALL")) return;

        int radius = config.getConfig().getInt("Mob-Hoppers.Settings.collect-radius-in-chunks");

        for (Chunk chunk : ILocationUtil.getChunksAroundLocation(e.getEntity().getEyeLocation(), radius)) {
            //iterate over all the tile entities in the chunk
            for (BlockState tile : chunk.getTileEntities()) {

                if (!(tile instanceof Hopper)) continue;

                Hopper hopper = (Hopper) tile;
                //check if has metadata
                //check if the mobhoppertype is the same
                if (!isMobHopperLocation(hopper.getLocation()))
                    continue;

                List<ItemStack> drops = e.getDrops();

                //all mob hopper
                if (getMobHopperType(hopper.getLocation()).equalsIgnoreCase("all")) {

                    //add items to the hopper
                    for (ItemStack itemStack : drops) {
                        if (itemStack == null) continue;
                        //no item spawn remove the not desired drops
                        hopper.getInventory().addItem(itemStack);
                    }

                    //clear the drops to not duplicate them
                    e.getDrops().clear();
                    return;
                }


                if (!getMobHopperType(hopper.getLocation()).equalsIgnoreCase(e.getEntity().getType().toString()))
                    continue;


                //add items to the hopper
                for (ItemStack itemStack : drops) {

                    if (itemStack == null) continue;
                    //no item spawn remove the not desired drops
                    hopper.getInventory().addItem(itemStack);
                }

                //clear the drops to not duplicate them
                e.getDrops().clear();
                return;
            }
        }

    }

    @EventHandler
    public void onMobHopperPlace(BlockPlaceEvent e) {
        String name = isMobHopper(e.getItemInHand());

        if (name.equalsIgnoreCase("nope")) return;

        if (!isOn())
            e.setCancelled(true);

        Player player = e.getPlayer();

        //mob hoppers disabled
        if (e.isCancelled()) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Mob-Hoppers.disabled-message"));
            return;
        }


        //no place permission
        if (!player.hasPermission(config.getConfig().getString("Mob-Hoppers.Settings.place-permission"))) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Mob-Hoppers.command.no-permission-msg"));
            e.setCancelled(true);
            return;
        }


        //set metadata to the block
        Block block = e.getBlockPlaced();

        addMobHopper(block.getLocation(), name);

    }

    @EventHandler
    public void onMobHopperBreak(BlockBreakEvent e) {
        //event cancelled or not a crop hopper
        if (!e.getBlock().getType().equals(Material.HOPPER) || !isMobHopperLocation(e.getBlock().getLocation()) || e.isCancelled())
            return;

        //no break permission
        if (!e.getPlayer().hasPermission(config.getConfig().getString("Mob-Hoppers.Settings.break-permission"))) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Mob-Hoppers.command.no-permission-msg"));
            return;
        }

        //get the info for the itemstack
        String mob = getMobHopperType(e.getBlock().getLocation());
        String name = ChatColor.translateAlternateColorCodes('&', stringUtil.replacePlaceholder(config.getConfig().getString("Mob-Hoppers.Item.name"), "{mob}", mob));
        List<String> lore = stringUtil.translateLore(config.getConfig().getStringList("Mob-Hoppers.Item.lore"));

        //create mob hopper
        ItemStack mobHopper = IInventoryUtil.createItem(1, Material.HOPPER, name, lore, true);

        //cancel event and break the hopper
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), mobHopper);


        removeMobHopper(e.getBlock().getLocation());
    }


    private String isMobHopper(ItemStack itemStack) {

        String mob = "nope";

        String path = "Mob-Hoppers.Item.";

        if (!itemStack.getType().equals(Material.HOPPER)) return mob;

        for (String mobName : config.getConfig().getStringList("Mob-Hoppers.Settings.mob-list")) {

            String name = ChatColor.translateAlternateColorCodes('&', stringUtil.replacePlaceholder(config.getConfig().getString(path + "name"), "{mob}", mobName));
            if (name.equalsIgnoreCase(itemStack.getItemMeta().getDisplayName()))
                mob = mobName;
        }

        return mob;
    }

}
