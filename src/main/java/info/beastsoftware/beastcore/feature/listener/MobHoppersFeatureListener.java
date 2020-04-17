package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.entity.MobHopper;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.MobHoppersService;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MobHoppersFeatureListener extends AbstractFeatureListener {


    private final MobHoppersService service;
    private final IDataConfig old;


    public MobHoppersFeatureListener(IConfig config, IConfig dataConfig, IDataConfig old) {
        super(config, FeatureType.MOB_HOPPERS);
        this.service = new MobHoppersService(dataConfig);
        this.old = old;
        this.loadLocations();
    }

    private void addMobHopper(Location location, String type) {
        this.service.add(location, type);
    }


    private void loadLocations() {

        for (Iterator<String> iterator = this.old.getConfigs().keySet().iterator(); iterator.hasNext();) {
            String mob = iterator.next();
            YamlConfiguration configuration = this.old.getConfigByName(mob);
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
                this.service.add(location, mob);
            }
        }
        this.old.deleteAll();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!isOn() || (e.getEntity() instanceof Player)) return;

        List<String> mobs = config.getConfig().getStringList("Mob-Hoppers.Settings.mob-list");

        if (!mobs.contains(e.getEntity().getType().toString()) && !mobs.contains("ALL")) return;

        int radius = config.getConfig().getInt("Mob-Hoppers.Settings.collect-radius-in-chunks");

        Entity entity = e.getEntity();
        EntityType type = entity.getType();
        Location location = entity.getLocation();

        //get all mobhoppers sorted by distance to the entity
        Set<MobHopper> hoppers = this.service.getInRadiusSortedByDistance(radius, location, type);
        List<ItemStack> dropsCopy = new ArrayList<>(e.getDrops());

        //iterate over all hoppers and try to add the drops there, near hoppers have preference
        for (MobHopper mobHopper : hoppers) {
            Inventory inventory = mobHopper.getInventory();

            //there are still items in the drops copy
            if (!dropsCopy.isEmpty()) {

                // iterate over all drops and add them in the hopper that can handle the amount of items
                for(Iterator<ItemStack> iterator = dropsCopy.iterator(); iterator.hasNext();){

                    ItemStack drop = iterator.next();
                    Material material = drop.getType();
                    int amount = drop.getAmount();

                    //check if the hopper allows this item to be added
                    boolean canAddDrop = IInventoryUtil.canAddItem(material, amount, inventory);

                    //if can be added, add and remove from the copy list
                    if(canAddDrop){
                        inventory.addItem(drop);
                        iterator.remove();
                    }
                }
            }

            // no more drops to add, stop looping
            else {
                break;
            }
        }

        //clear original drops and add the resulting drops after trying to insert them in near hoppers
        e.getDrops().clear();
        e.getDrops().addAll(dropsCopy);
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

        Location location = e.getBlock().getLocation();

        //event cancelled or not a crop hopper
        if (!e.getBlock().getType().equals(Material.HOPPER) || e.isCancelled())
            return;

        MobHopper mobHopper = this.service.fromLocation(location);

        //is not a hopper
        if(Objects.isNull(mobHopper)){
            return;
        }

        //no break permission
        if (!e.getPlayer().hasPermission(config.getConfig().getString("Mob-Hoppers.Settings.break-permission"))) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Mob-Hoppers.command.no-permission-msg"));
            return;
        }

        //get the info for the itemstack
        String mob = mobHopper.getType();
        String name = ChatColor.translateAlternateColorCodes('&', stringUtil.replacePlaceholder(config.getConfig().getString("Mob-Hoppers.Item.name"), "{mob}", mob));
        List<String> lore = stringUtil.translateLore(config.getConfig().getStringList("Mob-Hoppers.Item.lore"));

        //create mob hopper
        ItemStack hopperItem = IInventoryUtil.createItem(1, Material.HOPPER, name, lore, true);

        //cancel event and break the hopper
        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), hopperItem);

        //delete from memory and config
        this.service.delete(mobHopper);
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
