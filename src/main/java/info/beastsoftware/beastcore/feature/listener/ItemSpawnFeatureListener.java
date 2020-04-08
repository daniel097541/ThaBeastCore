package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnFeatureListener extends AbstractFeatureListener {

    public ItemSpawnFeatureListener(IConfig config) {
        super(config, FeatureType.ITEM_SPAWN);
    }




    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemSpawn(ItemSpawnEvent e) {
        try {
            if (!e.isCancelled() && isOn() && isListed(e.getEntity().getItemStack().getType())) {
                e.setCancelled(true);
            }
        }
        catch (Exception ex){
            Bukkit.getConsoleSender().sendMessage("Error in ItemSpawnFeature >> ");
            ex.printStackTrace();
        }
    }

    private boolean isListed(Material material) {
        try {
            return config.getConfig().getIntegerList("Deny-Item-Spawn.items").contains(material.getId());
        } catch (Exception e) {
            return false;
        }
    }
}
