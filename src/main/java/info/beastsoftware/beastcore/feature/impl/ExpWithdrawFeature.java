package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.ExpWithdraw;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.ExpWithdrawCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.ExpWithdrawFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.inventory.ItemStack;


@Singleton
public class ExpWithdrawFeature extends AbstractFeature {


    @Inject
    public ExpWithdrawFeature(@ExpWithdraw IConfig config,
                              BeastCore plugin) {

        super(config,new ExpWithdrawFeatureListener(config),new ExpWithdrawCommand(plugin,config), FeatureType.EXP_WITHDRAW);
    }


    public boolean isExpBottle(ItemStack itemStack){
        return ((ExpWithdrawFeatureListener) listener).isExpBottle(itemStack);
    }
}
