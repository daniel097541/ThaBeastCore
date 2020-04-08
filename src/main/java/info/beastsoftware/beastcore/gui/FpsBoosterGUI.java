package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.event.gui.FPSGuiOpenEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FpsBoosterGUI extends AbstractComplexGui {
    private List<String> loreEnabled;
    private List<String> loreDisabled;

    public FpsBoosterGUI(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig);
    }


    //PATHS



    @EventHandler
    public void onOpen(FPSGuiOpenEvent event){
        BeastPlayer player  = event.getPlayer();

        if(!existsInventory(player.getUuid().toString()))
            createInventory(player.getUuid().toString(),player.getUuid().toString());

        player.openInventory(getInventoryByName(player.getUuid().toString(), player.getUuid().toString()));
    }


    @Override
    public void createInventory(String name, String role) {
        String pathGUI = "FPS-Booster.gui.";

        int size = config.getConfig().getInt(pathGUI + "size");
        String title = config.getConfig().getString(pathGUI + "title");

        Inventory inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));

        //load the config if null
        if (dataConfig.getConfigByName(name) == null)
            dataConfig.loadConfig(name);


        //add every button to the inventory
        for (String buttonType : secondaryMenuButtons.keySet()) {

            if (buttonType.equalsIgnoreCase(ButtonType.SPACER.toString())) continue;

            InventoryButton button = secondaryMenuButtons.get(buttonType);

            //disabled, change lore
            if (!dataConfig.getConfigByName(name).getBoolean(button.getType().toString())) {
                ItemMeta meta = button.getButton().getItemMeta();
                meta.setLore(stringUtils.translateLore(loreDisabled));
                button.getButton().setItemMeta(meta);
            }

            inventory.setItem(button.getGuiIndex(), button.getButton());
        }


        //fill with spacers
        int slot = 0;
        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                slot++;
                continue;
            }
            inventory.setItem(slot, secondaryMenuButtons.get(ButtonType.SPACER.toString()).getButton());
            slot++;
        }


        inventories.put(name, new HashMap<>());
        inventories.get(name).put(name, inventory);
    }

    @Override
    public void createButtons() {
        String pathGUI = "FPS-Booster.gui.";

        String pathSpacer = pathGUI + "Buttons.spacer-button.";
        String pathInvisibleTnTButton = pathGUI + "Buttons." + ButtonType.TNT_TOGGLE.toString() + ".";
        String pathInvisibleSandButton = pathGUI + "Buttons." + ButtonType.SAND_TOGGLE.toString() + ".";
        String pathInvisibleDropsButton = pathGUI + "Buttons." + ButtonType.DROPS_TOGGLE.toString() + ".";
        String pathInvisibleMobsButton = pathGUI + "Buttons." + ButtonType.MOBS_TOGGLE.toString() + ".";
        String pathInvisiblePistonButton = pathGUI + "Buttons." + ButtonType.EXPLOSIONS_TOGGLE.toString() + ".";
        String pathInvisibleSpawnersButton = pathGUI + "Buttons." + ButtonType.SPAWNERS_TOGGLE.toString() + ".";
        String pathInvisibleParticlesButton = pathGUI + "Buttons." + ButtonType.PARTICLES_TOGGLE.toString() + ".";
        loreDisabled = config.getConfig().getStringList("FPS-Booster.gui.disabled-lore");
        loreEnabled = config.getConfig().getStringList("FPS-Booster.gui.enabled-lore");


        String dropsName = config.getConfig().getString(pathInvisibleDropsButton + "name");
        String mobsName = config.getConfig().getString(pathInvisibleMobsButton + "name");
        String sandName = config.getConfig().getString(pathInvisibleSandButton + "name");
        String pistonName = config.getConfig().getString(pathInvisiblePistonButton + "name");
        String spawnersName = config.getConfig().getString(pathInvisibleSpawnersButton + "name");
        String particlesName = config.getConfig().getString(pathInvisibleParticlesButton + "name");
        String tntName = config.getConfig().getString(pathInvisibleTnTButton + "name");

        Material spacerMat = Material.getMaterial(config.getConfig().getString(pathSpacer + "material"));
        Material matDrops = Material.getMaterial(config.getConfig().getString(pathInvisibleDropsButton + "material"));
        Material matMobs = Material.getMaterial(config.getConfig().getString(pathInvisibleMobsButton + "material"));
        Material matSand = Material.getMaterial(config.getConfig().getString(pathInvisibleSandButton + "material"));
        Material matPiston = Material.getMaterial(config.getConfig().getString(pathInvisiblePistonButton + "material"));
        Material matSpawners = Material.getMaterial(config.getConfig().getString(pathInvisibleSpawnersButton + "material"));
        Material matParticles = Material.getMaterial(config.getConfig().getString(pathInvisibleParticlesButton + "material"));
        Material matTnt = Material.getMaterial(config.getConfig().getString(pathInvisibleTnTButton + "material"));

        Short spacerDamage = new Short(config.getConfig().getString(pathSpacer + "damage"));
        Short damageDrops = new Short(config.getConfig().getString(pathInvisibleDropsButton + "damage"));
        Short damageMobs = new Short(config.getConfig().getString(pathInvisibleMobsButton + "damage"));
        Short damageSand = new Short(config.getConfig().getString(pathInvisibleSandButton + "damage"));
        Short damagePiston = new Short(config.getConfig().getString(pathInvisiblePistonButton + "damage"));
        Short damageSpawners = new Short(config.getConfig().getString(pathInvisibleSpawnersButton + "damage"));
        Short damageParticles = new Short(config.getConfig().getString(pathInvisibleParticlesButton + "damage"));
        Short damageTnT = new Short(config.getConfig().getString(pathInvisibleTnTButton + "damage"));

        int dropsPos = config.getConfig().getInt(pathInvisibleDropsButton + "position");
        int mobsPos = config.getConfig().getInt(pathInvisibleMobsButton + "position");
        int sandPos = config.getConfig().getInt(pathInvisibleSandButton + "position");
        int pistonPos = config.getConfig().getInt(pathInvisiblePistonButton + "position");
        int spawnerPos = config.getConfig().getInt(pathInvisibleSpawnersButton + "position");
        int particlesPos = config.getConfig().getInt(pathInvisibleParticlesButton + "position");
        int tntPos = config.getConfig().getInt(pathInvisibleTnTButton + "position");


        InventoryButton dropsButton = new InventoryButton(matDrops, dropsName, loreEnabled, damageDrops, ButtonType.DROPS_TOGGLE);
        dropsButton.setGuiIndex(dropsPos);

        InventoryButton mobsButton = new InventoryButton(matMobs, mobsName, loreEnabled, damageMobs, ButtonType.MOBS_TOGGLE);
        mobsButton.setGuiIndex(mobsPos);

        InventoryButton sandButton = new InventoryButton(matSand, sandName, loreEnabled, damageSand, ButtonType.SAND_TOGGLE);
        sandButton.setGuiIndex(sandPos);

        InventoryButton tntButton = new InventoryButton(matTnt, tntName, loreEnabled, damageTnT, ButtonType.TNT_TOGGLE);
        tntButton.setGuiIndex(tntPos);

        InventoryButton pistonButton = new InventoryButton(matPiston, pistonName, loreEnabled, damagePiston, ButtonType.EXPLOSIONS_TOGGLE);
        pistonButton.setGuiIndex(pistonPos);

        InventoryButton spawnerButton = new InventoryButton(matSpawners, spawnersName, loreEnabled, damageSpawners, ButtonType.SPAWNERS_TOGGLE);
        spawnerButton.setGuiIndex(spawnerPos);

        InventoryButton particlesButton = new InventoryButton(matParticles, particlesName, loreEnabled, damageParticles, ButtonType.PARTICLES_TOGGLE);
        particlesButton.setGuiIndex(particlesPos);

        InventoryButton spacerButton = new InventoryButton(spacerMat, " ", new ArrayList<>(), spacerDamage, ButtonType.SPACER);


        secondaryMenuButtons.put(dropsButton.getType().toString(), dropsButton);
        // secondaryMenuButtons.put(mobsButton.getType().toString(),mobsButton);
        secondaryMenuButtons.put(sandButton.getType().toString(), sandButton);
        secondaryMenuButtons.put(tntButton.getType().toString(), tntButton);
        secondaryMenuButtons.put(pistonButton.getType().toString(), pistonButton);
        // secondaryMenuButtons.put(spawnerButton.getType().toString(),spawnerButton);
        secondaryMenuButtons.put(particlesButton.getType().toString(), particlesButton);
        secondaryMenuButtons.put(spacerButton.getType().toString(), spacerButton);

    }

    @Override
    public void startMenus() {
        createButtons();
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();

        if (inventory == null) return;

        String name = null;

        for (String s1 : inventories.keySet()) {
            if (getInventoryByName(s1, s1).equals(inventory))
                name = s1;
        }

        if (name == null) return;


        ItemStack clickedItem = e.getCurrentItem();

        e.setCancelled(true);

        if (clickedItem == null) return;

        String itemType = null;

        for (String s1 : secondaryMenuButtons.keySet()) {
            if (secondaryMenuButtons.get(s1).getButton().equals(clickedItem))
                itemType = s1;
        }

        Player player = (Player) e.getWhoClicked();

        if (itemType == null || itemType.equalsIgnoreCase(ButtonType.SPACER.toString())) return;

        boolean b = !dataConfig.getConfigByName(player.getUniqueId().toString()).getBoolean(itemType);

        dataConfig.getConfigByName(player.getUniqueId().toString()).set(itemType, b);
        dataConfig.save(player.getUniqueId().toString());

        ItemMeta meta = clickedItem.getItemMeta();

        if (b) {
            meta.setLore(stringUtils.translateLore(loreEnabled));
        } else {
            meta.setLore(stringUtils.translateLore(loreDisabled));
        }

        secondaryMenuButtons.get(itemType).getButton().setItemMeta(meta);
        inventory.setItem(secondaryMenuButtons.get(itemType).getGuiIndex(), secondaryMenuButtons.get(itemType).getButton());

    }
}
