package info.beastsoftware.beastcore.feature;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.*;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FeatureManager implements IFeatureManager {



    @ItemFilter
    @Inject
    private IBeastFeature itemFilter;


    @Printer
    @Inject
    private IBeastFeature printer;


    @MainMenu
    @Inject
    private IBeastFeature mainMenu;


    @MainCommand
    @Inject
    private IBeastFeature mainCommand;


    public FeatureManager() {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eFeature manager initialized!"));
    }

    @Override
    public List<IBeastFeature> getFeatures() {

        Class clazz = this.getClass();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

        return fields
                .parallelStream()
                .peek(f -> f.setAccessible(true))
                .map(f -> {
                    IBeastFeature feature = null;
                    try {
                        feature = (IBeastFeature) f.get(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return feature;
                })
                .collect(Collectors.toList());

    }
}
