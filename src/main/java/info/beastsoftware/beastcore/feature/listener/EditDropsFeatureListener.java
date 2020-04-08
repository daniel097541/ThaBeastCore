package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.EditDropsGui;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class EditDropsFeatureListener extends AbstractFeatureListener {

    private final Random randomNumber;

    private final EditDropsGui gui;


    public EditDropsFeatureListener(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig, FeatureType.EDIT_DROPS);
        randomNumber = new Random();
        gui = new EditDropsGui(config,dataConfig);
    }




    private String getType(Entity entity) {
        String type = entity.getType().name();
        if (entity.getType().toString().equalsIgnoreCase(EntityType.IRON_GOLEM.toString())) {
            type = "GOLEM";
        }

        if (entity.getType().toString().equalsIgnoreCase(EntityType.CAVE_SPIDER.toString())) {
            type = "CAVESPIDER";
        }

        if (entity.getType().toString().equalsIgnoreCase(EntityType.PIG_ZOMBIE.toString())) {
            type = "ZOMBIE_PIGMAN";
        }

        return type;
    }

    public ItemStack[] getCustomDrops(Entity entity, boolean advancedDrops) {
        String name = getType(entity);
        ItemStack[] drops = new ItemStack[54];

        //advanced drops use
        if (advancedDrops) {

            YamlConfiguration configuration = dataConfig.getConfigByName(name);

            for (String section : configuration.getConfigurationSection("Item-List").getKeys(false)) {

                int id = Integer.parseInt(section);
                double chance = configuration.getDouble("Item-List." + id + ".chance");

                double random = Math.random();

                if (random > chance / 100) continue;

                ItemStack itemStack = configuration.getItemStack("Item-List." + id + ".item");

                drops[id] = itemStack;

            }

            return drops;
        }

        int item = randomNumber.nextInt(49);
        drops[0] = this.gui.getInventoryByName(name, name).getItem(item);
        return drops;
    }

    public boolean hasCustomDrops(Entity entity) {
        String type = getType(entity);

        if (dataConfig.getConfigByName(type) != null && dataConfig.getConfigByName(type).getConfigurationSection("Item-List") != null)
            if (dataConfig.getConfigByName(type).getConfigurationSection("Item-List").getKeys(false).size() > 0 && dataConfig.getConfigByName(type).getBoolean("Use-Advanced-Drops"))
                return true;

        return dataConfig.getConfigByName(type) != null && dataConfig.getConfigByName(type).get("Drops.Items") != null;
    }

    private boolean vanillaDropsDisabled(Entity entity) {

        String type = getType(entity);

        return !dataConfig.getConfigByName(type).getBoolean("Vanilla-Drops");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDie(EntityDeathEvent e) {
        if (!isOn()) return;
        Entity entity = e.getEntity();
        String type = getType(entity);
        if (hasCustomDrops(entity)) {

            if (vanillaDropsDisabled(entity))
                e.getDrops().clear();


            ItemStack[] drops = getCustomDrops(entity, dataConfig.getConfigByName(type).getBoolean("Use-Advanced-Drops"));

            for (ItemStack item : drops)
                if (item != null) {
                    e.getDrops().add(item);
                }
        }
    }


}
