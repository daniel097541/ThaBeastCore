package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.event.gui.ItemFilterGuiOpenEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.controller.ItemFilterController;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemFilterFeatureListener extends AbstractFeatureListener {


    public ItemFilterFeatureListener(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig, FeatureType.ITEM_FILTER);
        itemFilterController = new ItemFilterController(config,dataConfig, ItemFilterGuiOpenEvent.class, BeastCore.getInstance());
    }


    private final ItemFilterController itemFilterController;


    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Material material = event.getItem().getItemStack().getType();
        try {
            if (!dataConfig.getConfigByName(player.getUniqueId().toString()).getBoolean("Filtered-Materials." + material.toString() + ".pickup-enabled")) {
                event.setCancelled(true);
            }
        } catch (NullPointerException ignored) {

        }
    }
}
