package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.entity.CropHopper;
import info.beastsoftware.beastcore.event.itemgiveevent.CropHopperGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.CropHoppersGui;
import info.beastsoftware.beastcore.manager.CropHoppersDataManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastChunk;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CropHoppersFeatureListener extends AbstractFeatureListener {

    private final StringUtils stringUtil = new StringUtils();
    private final CropHoppersDataManager cropHoppersManager;

    public CropHoppersFeatureListener(IConfig config, IConfig dataConfig) {
        super(config, FeatureType.CROP_HOPPERS);
        this.cropHoppersManager = new CropHoppersDataManager(dataConfig);

        try {
            this.updateData();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateData() {

        //no need to convert data
        if (this.getConfig().getConfig().getConfigurationSection("Crop-Hoppers.locations") == null) {
            return;
        }

        for (String id : this.getConfig().getConfig().getConfigurationSection("Crop-Hoppers.locations").getKeys(false)) {

            String worldName = this.getConfig().getConfig().getString("Crop-Hoppers.locations." + id + ".world");
            World world = Bukkit.getWorld(worldName);

            if (world == null) continue;

            int x = this.getConfig().getConfig().getInt("Crop-Hoppers.locations." + id + ".x");
            int y = this.getConfig().getConfig().getInt("Crop-Hoppers.locations." + id + ".y");
            int z = this.getConfig().getConfig().getInt("Crop-Hoppers.locations." + id + ".z");

            Location location = new Location(world, x, y, z);
            this.cropHoppersManager.saveHopper(new CropHopper(location));
        }

        //remove this section completly
        this.config.getConfig().set("Crop-Hoppers.locations", null);
        this.config.save();
    }


    private boolean isCropLocation(Location location) {
        return Objects.nonNull(this.cropHoppersManager.get(location));
    }


    private void addCropLocation(Location location) {
        this.cropHoppersManager.saveHopper(new CropHopper(location));
    }

    private void removeLocation(Location location) {

        CropHopper hopperAt = this.cropHoppersManager.get(location);

        //id not found
        if (Objects.isNull(hopperAt)) {
            return;
        }

        //remove hopper
        this.cropHoppersManager.removeHopper(hopperAt);
    }


    @EventHandler
    public void onItemPlace(BlockPlaceEvent e) {

        if (!isCropHopper(e.getItemInHand())) return;

        if (!isOn())
            e.setCancelled(true);

        Player player = e.getPlayer();

        //crop hoppers disabled
        if (e.isCancelled()) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Crop-Hoppers.disabled-message"));
            return;
        }

        //no place permission
        if (!player.hasPermission(config.getConfig().getString("Crop-Hoppers.Settings.place-permission"))) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Crop-Hoppers.command.no-permission-msg"));
            e.setCancelled(true);
            return;
        }

        //set metadata to the block
        Block block = e.getBlockPlaced();

        //add crop location
        addCropLocation(block.getLocation());

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        //event cancelled or not a crop hopper
        if (!e.getBlock().getType().equals(Material.HOPPER) || !isCropLocation(e.getBlock().getLocation()) || e.isCancelled())
            return;

        //no break permission
        if (!e.getPlayer().hasPermission(config.getConfig().getString("Crop-Hoppers.Settings.break-permission"))) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Crop-Hoppers.command.no-permission-msg"));
            return;
        }


        //spawn the crophopper item at break location
        Block block = e.getBlock();
        Location location = block.getLocation();

        removeLocation(block.getLocation());


        location.getWorld().dropItem(location, createCropHopper(1));

        //break the block with no drop and remove metadata
        e.setCancelled(true);
        block.setType(Material.AIR);


        //remove crop hopper from locations
    }


    private ItemStack createCropHopper(int amount) {
        //info for the hopper
        String basePath = "Crop-Hoppers.Item.";
        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString(basePath + "name"));
        List<String> lore = stringUtil.translateLore(config.getConfig().getStringList(basePath + "lore"));

        //create the hopper
        return IInventoryUtil.createItem(amount, Material.HOPPER, name, lore, true);
    }


    private boolean insertHere(CropHopper cropHopper) {
        Hopper hopper = cropHopper.getHopper();
        return IInventoryUtil.hasEmptySlot(hopper.getInventory());
    }


    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        try {

            if (!isOn() || e.isCancelled()) return;

            if (!CropHoppersGui.getInstance().isItemContained(e.getEntity().getItemStack()))
                return;

            int radius = config.getConfig().getInt("Crop-Hoppers.Settings.collect-radius-in-chunks");

            boolean factionsMode = config.getConfig().getBoolean("Crop-Hoppers.Settings.factions-mode");
            String id;
            if (this.isFactionsHooked()) {
                id = this.getAtLocation(e.getLocation()).getId();
                //crop hopper in wilderness = not working
                try {
                    if (factionsMode && Integer.parseInt(id) <= 0) return;
                } catch (Exception ignored) {
                    return;
                }
            }


            Item item = e.getEntity();
            Location itemLocation = item.getLocation();
            BeastChunk spawnedChunk = new BeastChunkImpl(itemLocation.getChunk());

            CropHopper crop = null;
            Set<CropHopper> hoppers = this.cropHoppersManager.findAllInChunkAndRadius(spawnedChunk, radius, item.getItemStack());

            //no near hopper
            if (hoppers.isEmpty()) {
                return;
            }

            double lastDistance = Double.MAX_VALUE;

            //find nearest
            for (CropHopper cropHopper : hoppers) {
                double distance = cropHopper.getLocation().distance(itemLocation);
                if (distance < lastDistance) {
                    lastDistance = distance;
                    crop = cropHopper;
                }
            }

            //put item into crop hopper
            crop.addItem(item.getItemStack());
            e.setCancelled(true);
        } catch (Exception ignored) {
        }

    }


    @EventHandler
    public void onCropHoppersGive(CropHopperGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        int number = event.getAmount();
        //create the hopper
        ItemStack itemStack = createCropHopper(number);
        //add item to the inventory
        player.getInventory().addItem(itemStack);
    }


    private boolean isCropHopper(ItemStack itemStack) {
        if (!itemStack.getType().equals(Material.HOPPER)) return false;
        ItemMeta meta = itemStack.getItemMeta();
        String displayname = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Crop-Hoppers.Item.name"));
        if (meta == null || meta.getDisplayName() == null) return false;
        if (!meta.getDisplayName().equals(displayname)) return false;
        if (!Bukkit.getBukkitVersion().contains("1.7"))
            return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) && meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE);
        return true;
    }

}
