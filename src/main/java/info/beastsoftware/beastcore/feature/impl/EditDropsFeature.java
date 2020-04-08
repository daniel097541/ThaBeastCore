package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.EditDrops;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.EditDropsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.EditDropsFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;



@Singleton
public class EditDropsFeature extends AbstractFeature {

    @Inject
    public EditDropsFeature(@EditDrops IConfig config,
                            @EditDrops IDataConfig dataConfig,
                            BeastCore plugin) {
        super(config,
                new EditDropsFeatureListener(config, dataConfig),
                new EditDropsCommand(plugin, config),
                dataConfig, FeatureType.EDIT_DROPS);
    }


    public ItemStack[] getSimpleDrops(Entity entity){
        EditDropsFeatureListener listener = (EditDropsFeatureListener) this.getListener();
        return listener.getCustomDrops(entity,false);
    }

    public boolean hasCustomDrops(Entity entity){
        EditDropsFeatureListener listener = (EditDropsFeatureListener) this.getListener();
        return listener.hasCustomDrops(entity);
    }

    public ItemStack[] getAdvancedDrops(Entity entity){
        EditDropsFeatureListener listener = (EditDropsFeatureListener) this.getListener();
        return listener.getCustomDrops(entity,true);
    }



}
