package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.gui.controller.IGUIController;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.configuration.manager.IConfigManager;
import info.beastsoftware.beastcore.configuration.manager.IDataConfigManager;
import info.beastsoftware.beastcore.event.gui.ItemFilterGuiOpenEvent;
import info.beastsoftware.beastcore.event.gui.MainGuiOpenEvent;
import info.beastsoftware.beastcore.gui.*;
import info.beastsoftware.beastcore.gui.controller.ItemFilterController;
import info.beastsoftware.beastcore.gui.controller.MainMenuController;
import info.beastsoftware.beastcore.struct.ComplexGui;
import info.beastsoftware.beastcore.struct.ConfigType;
import info.beastsoftware.beastcore.struct.GUIType;
import info.beastsoftware.beastcore.struct.SimpleGui;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class GUIManager implements IGUIManager{



    private final HashMap<SimpleGui, ISimpleGui> simpleGuis = new HashMap<>();
    private final HashMap<ComplexGui, IComplexGui> complexGuis = new HashMap<>();
    private final HashMap<GUIType, IGUIController> guiControllers = new HashMap<>();


    private final IHookManager hookManager;
    private final BeastCore plugin;
    private final IConfigManager configManager;
    private final IDataConfigManager dataConfigManager;
    
    @Inject
    public GUIManager(IHookManager hookManager, BeastCore plugin, IConfigManager configManager, IDataConfigManager dataConfigManager) {
        this.hookManager = hookManager;
        this.plugin = plugin;
        this.configManager = configManager;
        this.dataConfigManager = dataConfigManager;
        this.load();
    }


    @Override
    public void load() {
        
        
        this.loadSimpleGUIs();
        this.loadComplexGUIs();
        
        
    }
    
    
    private void loadComplexGUIs(){
        
        try {
            if (this.hookManager.isFactionsHooked())
                complexGuis.put(ComplexGui.ALERTS, new AlertsGui(this.configManager.getByType(ConfigType.ALERTS_CONFIG), this.dataConfigManager.getByType(ConfigType.ALERTS_CONFIG)));
        } catch (Exception e) {
            throwGuiException(ComplexGui.ALERTS, e);
        }


        try {
            complexGuis.put(ComplexGui.DROPS, new EditDropsGui(this.configManager.getByType(ConfigType.DROPS_CONFIG), this.dataConfigManager.getByType(ConfigType.DROPS_CONFIG)));
        } catch (Exception e) {
            throwGuiException(ComplexGui.DROPS, e);
        }


        try {
            complexGuis.put(ComplexGui.FPS_BOOSTER, new FpsBoosterGUI(this.configManager.getByType(ConfigType.FPS_BOOSTER), this.dataConfigManager.getByType(ConfigType.FPS_BOOSTER)));
        } catch (Exception e) {
            throwGuiException(ComplexGui.FPS_BOOSTER, e);
        }


        guiControllers.put(GUIType.ITEM_FILTER, new ItemFilterController(this.configManager.getByType(ConfigType.ITEMFILTER), this.dataConfigManager.getByType(ConfigType.ITEMFILTER), ItemFilterGuiOpenEvent.class, this.plugin));
        guiControllers.put(GUIType.MAIN_MENU, new MainMenuController(this.configManager.getByType(ConfigType.FEATURES_MENU), MainGuiOpenEvent.class, this.plugin));


        //register all guis as listeners
        for (Map.Entry<ComplexGui, IComplexGui> entry : complexGuis.entrySet()) {

            ComplexGui key = entry.getKey();
            IComplexGui complexGui = entry.getValue();

            try {
                this.plugin.getServer().getPluginManager().registerEvents(complexGui, this.plugin);
            } catch (Exception e) {
                throwGuiException(key, e);
            }
        }
    }
    
    
    
    
    private void loadSimpleGUIs(){

        try {
            simpleGuis.put(SimpleGui.ANTIITEMCRAFT, new AntiItemCraftGui(this.configManager.getByType(ConfigType.ANTIITEMCRAFT_CONFIG)));
        } catch (Exception e) {
            throwGuiException(SimpleGui.ANTIITEMCRAFT, e);
        }


        try {
            simpleGuis.put(SimpleGui.HOPPER_FILTER, new HopperFilterGui(this.configManager.getByType(ConfigType.HOPPER_FILTER_CONFIG)));
        } catch (Exception e) {
            throwGuiException(SimpleGui.HOPPER_FILTER, e);
        }


        try {
            simpleGuis.put(SimpleGui.CROP_HOPPERS, new CropHoppersGui(this.configManager.getByType(ConfigType.CROP_HOPPERS)));
        } catch (Exception e) {
            throwGuiException(SimpleGui.CROP_HOPPERS, e);
        }


        try {
            simpleGuis.put(SimpleGui.BLOCKS_GUI, new CombatBlockPlaceGui(this.configManager.getByType(ConfigType.COMBAT_CONFIG)));
        } catch (Exception e) {
            throwGuiException(SimpleGui.BLOCKS_GUI, e);
        }


        try {
            simpleGuis.put(SimpleGui.COMBAT, new CombatGui(this.configManager.getByType(ConfigType.COMBAT_CONFIG), getSimpleGUI(SimpleGui.BLOCKS_GUI)));
        } catch (Exception e) {
            throwGuiException(SimpleGui.COMBAT, e);
        }



        //register all simple guis listeners
        for (Map.Entry<SimpleGui, ISimpleGui> entry : simpleGuis.entrySet()) {

            ISimpleGui simpleGui = entry.getValue();
            SimpleGui key = entry.getKey();

            try {
                this.plugin.getServer().getPluginManager().registerEvents(simpleGui, this.plugin);
            } catch (Exception e) {
                throwGuiException(key, e);
            }
        }
    }
    
    

    @Override
    public ISimpleGui getSimpleGUI(SimpleGui guiType) {
        return this.simpleGuis.get(guiType);
    }

    @Override
    public IComplexGui getComplexGUI(ComplexGui guiType) {
        return this.complexGuis.get(guiType);
    }


    private void throwGuiException(Enum key, Exception e) {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &4WARNING!!! &cThere was an error creating the " + key.name() +
                " GUI, please check that the config is correct, if you cant find the bug please delete it and restart!"));
        e.printStackTrace();
    }

    @Override
    public void reload() {
        this.simpleGuis
                .values()
                .parallelStream()
                .forEach(ISimpleGui::reload);

        this.complexGuis
                .values()
                .parallelStream()
                .forEach(IComplexGui::reload);
    }
}
