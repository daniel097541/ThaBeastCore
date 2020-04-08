package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.CombatGuiOpenEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombatGui extends AbstractSimpleGui {
    private HashMap<ButtonType, InventoryButton> buttons;


    private final ISimpleGui blocksGUI;

    public CombatGui(IConfig config, ISimpleGui blocksGUI) {
        super(config);
        this.blocksGUI = blocksGUI;
        reload();
    }

    @EventHandler
    public void onGuiOpen(CombatGuiOpenEvent event) {
        BeastPlayer player = event.getPlayer();
        player.openInventory(getMenu());
    }

    @Override
    public void createInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Combat-Tag.gui.title"));
        int size = config.getConfig().getInt("Combat-Tag.gui.size");
        menu = Bukkit.createInventory(null, size, title);
    }

    @Override
    public void createButtons() {
        String guiPath = "Combat-Tag.gui.";
        String guiButtons = guiPath + "Buttons.";

        String toggleName = config.getConfig().getString(guiButtons + "Toggle-Button.name");
        Material toggleMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Toggle-Button.material"));
        Short togleDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Toggle-Button.damage") + "");
        List<String> toggleEnabledLore = config.getConfig().getStringList(guiButtons + "Toggle-Button.enabled-lore");
        List<String> toggleDisabledLore = config.getConfig().getStringList(guiButtons + "Toggle-Button.disabled-lore");
        int togglePos = config.getConfig().getInt(guiButtons + "Toggle-Button.position-in-gui");

        String closeName = config.getConfig().getString(guiButtons + "Close-Button.name");
        Material closeMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Close-Button.material"));
        Short closeDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Close-Button.damage") + "");
        List<String> closeLore = config.getConfig().getStringList(guiButtons + "Close-Button.enabled-lore");
        int closePos = config.getConfig().getInt(guiButtons + "Close-Button.position-in-gui");

        String blocksName = config.getConfig().getString(guiButtons + "Denied-Blocks-Button.name");
        Material blocksMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Denied-Blocks-Button.material"));
        Short blocksDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Denied-Blocks-Button.damage") + "");
        List<String> blocksLore = config.getConfig().getStringList(guiButtons + "Denied-Blocks-Button.lore");
        int blocksPos = config.getConfig().getInt(guiButtons + "Denied-Blocks-Button.position-in-gui");

        String flyName = config.getConfig().getString(guiButtons + "Cancel-Fly-Button.name");
        Material flyMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Cancel-Fly-Button.material"));
        Short flyDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Cancel-Fly-Button.damage") + "");
        List<String> flyEnabledLore = config.getConfig().getStringList(guiButtons + "Cancel-Fly-Button.enabled-lore");
        List<String> flyDisabledLore = config.getConfig().getStringList(guiButtons + "Cancel-Fly-Button.disabled-lore");
        int flyPos = config.getConfig().getInt(guiButtons + "Cancel-Fly-Button.position-in-gui");

        String pearlName = config.getConfig().getString(guiButtons + "Reset-On-Pearl-Button.name");
        Material pearlMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Reset-On-Pearl-Button.material"));
        Short pearlDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Reset-On-Pearl-Button.damage") + "");
        List<String> pearlEnabledLore = config.getConfig().getStringList(guiButtons + "Reset-On-Pearl-Button.enabled-lore");
        List<String> pearlDisabledLore = config.getConfig().getStringList(guiButtons + "Reset-On-Pearl-Button.disabled-lore");
        int pearlPos = config.getConfig().getInt(guiButtons + "Reset-On-Pearl-Button.position-in-gui");

        String durationName = config.getConfig().getString(guiButtons + "Combat-Duration-Button.name");
        Material durationMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Combat-Duration-Button.material"));
        Short durationDamage = Short.valueOf(config.getConfig().getInt(guiButtons + "Combat-Duration-Button.damage") + "");
        List<String> durationLore = config.getConfig().getStringList(guiButtons + "Combat-Duration-Button.lore");
        int durationPos = config.getConfig().getInt(guiButtons + "Combat-Duration-Button.position-in-gui");


        closeButton = new InventoryButton(closeMat, closeName, closeLore, closeDamage, ButtonType.CLOSE);
        closeButton.setGuiIndex(closePos);

        if (config.getConfig().getBoolean("Combat-Tag.enabled"))
            toggleButton = new InventoryButton(toggleMat, toggleName, toggleEnabledLore, togleDamage, ButtonType.TOGGLE);
        else
            toggleButton = new InventoryButton(toggleMat, toggleName, toggleDisabledLore, togleDamage, ButtonType.TOGGLE);

        toggleButton.setGuiIndex(togglePos);

        InventoryButton blocksButton = new InventoryButton(blocksMat, blocksName, blocksLore, blocksDamage, ButtonType.BLOCK_LIST);
        blocksButton.setGuiIndex(blocksPos);

        InventoryButton flyButton;

        if (config.getConfig().getBoolean("Combat-Tag.cancel-fly"))
            flyButton = new InventoryButton(flyMat, flyName, flyEnabledLore, flyDamage, ButtonType.CANCEL_FLY);
        else flyButton = new InventoryButton(flyMat, flyName, flyDisabledLore, flyDamage, ButtonType.CANCEL_FLY);

        flyButton.setGuiIndex(flyPos);

        InventoryButton pearlButton;

        if (config.getConfig().getBoolean("Combat-Tag.reset-on-enderpearl"))
            pearlButton = new InventoryButton(pearlMat, pearlName, pearlEnabledLore, pearlDamage, ButtonType.PEARL_BUTTON);
        else
            pearlButton = new InventoryButton(pearlMat, pearlName, pearlDisabledLore, pearlDamage, ButtonType.PEARL_BUTTON);

        pearlButton.setGuiIndex(pearlPos);

        List<String> durationTrans = new ArrayList<>();
        StringUtils stringUtil = new StringUtils();

        for (String line : durationLore) {
            String newLine = stringUtil.replacePlaceholder(line, "{combat_duration}", config.getConfig().getInt("Combat-Tag.duration") + "");
            newLine = stringUtil.replacePlaceholder(newLine, "{increase_by_click}", config.getConfig().getInt(guiButtons + "Combat-Duration-Button.increase-by-click") + "");
            newLine = stringUtil.replacePlaceholder(newLine, "{decrease_by_click}", config.getConfig().getInt(guiButtons + "Combat-Duration-Button.decrease-by-click") + "");
            durationTrans.add(newLine);
        }

        InventoryButton durationButton = new InventoryButton(durationMat, durationName, durationTrans, durationDamage, ButtonType.DURATION);

        durationButton.setGuiIndex(durationPos);

        buttons.put(durationButton.getType(), durationButton);
        buttons.put(pearlButton.getType(), pearlButton);
        buttons.put(flyButton.getType(), flyButton);
        buttons.put(blocksButton.getType(), blocksButton);

    }


    @Override
    @EventHandler
    public void onClick(InventoryClickEvent e) {


        Inventory inventory = e.getClickedInventory();
        ItemStack itemStack = e.getCurrentItem();

        if (inventory == null || itemStack == null) return;

        if (!inventory.equals(menu)) return;

        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        //close button
        if (itemStack.equals(closeButton.getButton())) {
            player.closeInventory();
            return;
        }

        //toggle
        if (itemStack.equals(toggleButton.getButton())) {
            config.getConfig().set("Combat-Tag.enabled", !config.getConfig().getBoolean("Combat-Tag.enabled"));
            config.save();
            createButtons();
            initGui();
            return;
        }


        //toggle pearl
        if (itemStack.equals(buttons.get(ButtonType.PEARL_BUTTON).getButton())) {
            config.getConfig().set("Combat-Tag.reset-on-enderpearl", !config.getConfig().getBoolean("Combat-Tag.reset-on-enderpearl"));
            config.save();
            createButtons();
            initGui();
            return;
        }

        //toggle fly
        if (itemStack.equals(buttons.get(ButtonType.CANCEL_FLY).getButton())) {
            config.getConfig().set("Combat-Tag.cancel-fly", !config.getConfig().getBoolean("Combat-Tag.cancel-fly"));
            config.save();
            createButtons();
            initGui();
            return;
        }

        //button list click
        if (itemStack.equals(buttons.get(ButtonType.BLOCK_LIST).getButton())) {
            player.closeInventory();
            player.openInventory(this.blocksGUI.getMenu());
            return;
        }

        //add duration
        if (itemStack.equals(buttons.get(ButtonType.DURATION).getButton())) {
            String guiPath = "Combat-Tag.gui.";
            String guiButtons = guiPath + "Buttons.";
            int duration = config.getConfig().getInt("Combat-Tag.duration");

            if (e.getAction().equals(InventoryAction.PICKUP_ALL))
                duration += config.getConfig().getInt(guiButtons + "Combat-Duration-Button.increase-by-click");
            if (e.getAction().equals(InventoryAction.PICKUP_HALF))
                duration -= config.getConfig().getInt(guiButtons + "Combat-Duration-Button.decrease-by-click");

            if (duration < 0)
                duration = 0;

            config.getConfig().set("Combat-Tag.duration", duration);
            config.save();
            createButtons();
            initGui();
        }


    }

    @Override
    public void reload() {
        this.buttons = new HashMap<>();
        createInventory();
        createButtons();
        initGui();
    }

    @Override
    public void initGui() {

        menu.setItem(closeButton.getGuiIndex(), closeButton.getButton());
        menu.setItem(toggleButton.getGuiIndex(), toggleButton.getButton());

        for (InventoryButton button : buttons.values()) {
            menu.setItem(button.getGuiIndex(), button.getButton());
        }
    }

}
