package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.event.gui.AlertsGuiOpenEvent;
import info.beastsoftware.beastcore.struct.Alerts;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.beastcore.struct.Role;
import info.beastsoftware.hookcore.entity.BeastFaction;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class AlertsGui extends AbstractComplexGui {
    public AlertsGui(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BeastCore.getInstance(), () -> {
            for (BeastFaction f : getAllFactions()) {
                dataConfig.loadConfig(f.getId());
            }
        }, 60L);
    }


    @EventHandler
    public void onGuiOpen(AlertsGuiOpenEvent event) {
        BeastPlayer player = event.getPlayer();
        player.openInventory(getMainMenu());
    }

    @Override
    public void startMenus() {
        mainMenu = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Faction-Alerts.gui.Title")));
        createButtons();
        for (InventoryButton button : mainMenuButtons.values()) {
            mainMenu.setItem(button.getGuiIndex(), button.getButton());
        }

        //////////////  FILL WITH SPACERS /////////
        ItemStack spacer = new ItemStack(Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Spacer.material")));
        ItemMeta meta = spacer.getItemMeta();
        meta.setDisplayName(" ");

        //1.7 has no ItemFlag class
        if (!Bukkit.getBukkitVersion().contains("1.7"))
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        spacer.setItemMeta(meta);
        spacer.setDurability(new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Back.button-damage-id")));
        int cont = 0;
        for (ItemStack itemStack : mainMenu) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                mainMenu.setItem(cont, spacer);
            cont++;
        }
        //////////////  FILL WITH SPACERS /////////
    }

    @Override
    public void createInventory(String name, String role) {

        if (dataConfig.getConfigByName(name) == null) {
            dataConfig.loadConfig(name);
        }

        Inventory inventory = Bukkit.createInventory(null, 54, role);

        if (!inventories.containsKey(name))
            inventories.put(name, new HashMap<>());

        inventories.get(name).put(role, inventory);


        //////////////    SECONDARY MENU BUTTONS /////////////////
        Material enabledMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Enabled.material"));
        Material disabledMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Disabled.material"));
        Material spacerMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Spacer.material"));

        Material backMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Buttons.Back.button-material"));
        String backName = config.getConfig().getString("Faction-Alerts.gui.Buttons.Back.button-name");
        List<String> backLore = config.getConfig().getStringList("Faction-Alerts.gui.Buttons.Back.button-lore");
        Short backDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Back.button-damage-id"));
        int backPos = config.getConfig().getInt("Faction-Alerts.gui.Buttons.Back.position-in-gui");

        InventoryButton backButton = new InventoryButton(backMaterial, backName, backLore, backDamage, ButtonType.BACK);
        backButton.setGuiIndex(backPos);

        secondaryMenuButtons.put(ButtonType.BACK.toString(), backButton);
        inventories.get(name).get(role).setItem(backButton.getGuiIndex(), backButton.getButton());

        Short enabledDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Enabled.damage"));
        Short disabledDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Disabled.damage"));
        Short spacerDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Spacer.damage"));

        List<String> enabledLore = config.getConfig().getStringList("Faction-Alerts.gui.Enabled.lore");
        List<String> disabledLore = config.getConfig().getStringList("Faction-Alerts.gui.Disabled.lore");

        for (Alerts alert : Alerts.values()) {
            if (config.getConfig().getBoolean("Faction-Alerts.gui." + alert.toString() + ".enabled")) {
                String alertName = config.getConfig().getString("Faction-Alerts.gui." + alert.toString() + ".name");
                int position = config.getConfig().getInt("Faction-Alerts.gui." + alert.toString() + ".position-in-gui");
                InventoryButton alertButton;
                if (dataConfig.getConfigByName(name).getBoolean(role + "." + alert.toString()))
                    alertButton = new InventoryButton(enabledMaterial, alertName, enabledLore, enabledDamage, ButtonType.ALERT);
                else
                    alertButton = new InventoryButton(disabledMaterial, alertName, disabledLore, disabledDamage, ButtonType.ALERT);
                alertButton.setGuiIndex(position);
                secondaryMenuButtons.put(alert.toString(), alertButton);
                inventories.get(name).get(role).setItem(alertButton.getGuiIndex(), alertButton.getButton());
            }
        }
        //////////////    SECONDARY MENU BUTTONS /////////////////


        //////////////  FILL WITH SPACERS /////////
        ItemStack spacer = new ItemStack(spacerMaterial);
        ItemMeta meta = spacer.getItemMeta();
        meta.setDisplayName(" ");

        if (!Bukkit.getBukkitVersion().contains("1.7"))
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        spacer.setItemMeta(meta);
        spacer.setDurability(spacerDamage);
        int cont = 0;
        for (ItemStack itemStack : inventories.get(name).get(role)) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                inventories.get(name).get(role).setItem(cont, spacer);
            cont++;
        }
        //////////////  FILL WITH SPACERS /////////


    }


    @Override
    public void createButtons() {

        //////////////    MAIN MENU BUTTONS /////////////////////
        Material coleadersMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Buttons.Coleader.button-material"));
        Material membersMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Buttons.Member.button-material"));
        Material modsMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Buttons.Moderator.button-material"));
        Material adminsMaterial = Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Buttons.Admin.button-material"));


        Short coleadersDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Coleader.button-damage-id"));
        Short membersDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Member.button-damage-id"));
        Short modsDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Moderator.button-damage-id"));
        Short adminsDamage = new Short(config.getConfig().getString("Faction-Alerts.gui.Buttons.Admin.button-damage-id"));


        List<String> coleadersLore = config.getConfig().getStringList("Faction-Alerts.gui.Buttons.Coleader.button-lore");
        List<String> membersLore = config.getConfig().getStringList("Faction-Alerts.gui.Buttons.Member.button-lore");
        List<String> modsLore = config.getConfig().getStringList("Faction-Alerts.gui.Buttons.Moderator.button-lore");
        List<String> adminsLore = config.getConfig().getStringList("Faction-Alerts.gui.Buttons.Admin.button-lore");


        String coleadersName = config.getConfig().getString("Faction-Alerts.gui.Buttons.Coleader.button-name");
        String membersName = config.getConfig().getString("Faction-Alerts.gui.Buttons.Member.button-name");
        String modsName = config.getConfig().getString("Faction-Alerts.gui.Buttons.Moderator.button-name");
        String adminsName = config.getConfig().getString("Faction-Alerts.gui.Buttons.Admin.button-name");


        InventoryButton coleadersButton = new InventoryButton(coleadersMaterial, coleadersName, coleadersLore, coleadersDamage, ButtonType.COLEADER);
        coleadersButton.setGuiIndex(config.getConfig().getInt("Faction-Alerts.gui.Buttons.Coleader.position-in-gui"));
        InventoryButton membersButton = new InventoryButton(membersMaterial, membersName, membersLore, membersDamage, ButtonType.MEMBER);
        membersButton.setGuiIndex(config.getConfig().getInt("Faction-Alerts.gui.Buttons.Member.position-in-gui"));
        InventoryButton modsButton = new InventoryButton(modsMaterial, modsName, modsLore, modsDamage, ButtonType.MOD);
        modsButton.setGuiIndex(config.getConfig().getInt("Faction-Alerts.gui.Buttons.Moderator.position-in-gui"));
        InventoryButton adminsButton = new InventoryButton(adminsMaterial, adminsName, adminsLore, adminsDamage, ButtonType.ADMIN);
        adminsButton.setGuiIndex(config.getConfig().getInt("Faction-Alerts.gui.Buttons.Admin.position-in-gui"));

        mainMenuButtons.put(ButtonType.COLEADER.toString(), coleadersButton);
        mainMenuButtons.put(ButtonType.MEMBER.toString(), membersButton);
        mainMenuButtons.put(ButtonType.MOD.toString(), modsButton);
        mainMenuButtons.put(ButtonType.ADMIN.toString(), adminsButton);
        //////////////    MAIN MENU BUTTONS /////////////////////
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        BeastPlayer player = this.getPlayer((Player) e.getWhoClicked());
        BeastFaction faction = player.getMyFaction();
        String facId = faction.getId();

        if (inventory == null || item == null) return;

        if (inventory.equals(mainMenu)) {
            e.setCancelled(true);
            //main menu stuff
            ButtonType type = null;
            for (InventoryButton button : mainMenuButtons.values()) {
                if (button.getButton().equals(item)) {
                    type = button.getType();
                    break;
                }
            }
            if (type == null)
                return;
            if (inventories.get(facId) == null) {
                for (Role role : Role.values())
                    createInventory(facId, role.name());
            }
            Role role = Role.valueOf(type.name());
            e.getWhoClicked().openInventory(inventories.get(facId).get(role.name()));
            return;
        }

        if (!containsInventory(facId, inventory)) return;

        e.setCancelled(true);

        //pressed back button
        if (secondaryMenuButtons.get(ButtonType.BACK.toString()).getButton().equals(e.getCurrentItem())) {
            player.openInventory(mainMenu);
            return;
        }

        //start searching for the pressed button
        Role role = null;
        Alerts alert = null;
        //look for the clicked role inventory
        for (String stRole : inventories.get(facId).keySet()) {
            if (inventories.get(facId).get(stRole).equals(inventory)) {
                role = Role.valueOf(stRole);
            }
        }

        //look for the clicked alert
        for (Alerts alerts : Alerts.values()) {
            if (ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Faction-Alerts.gui." + alerts.toString() + ".name")).equalsIgnoreCase(item.getItemMeta().getDisplayName()))
                alert = alerts;
        }
        //not clicked an alert
        if (alert == null || role == null) {
            return;
        }

        //save in config
        YamlConfiguration dataConf = dataConfig.getConfigByName(facId);
        dataConf.set(role.toString() + "." + alert.toString(), !dataConf.getBoolean(role.toString() + "." + alert.toString()));
        dataConfig.save(facId);
        ItemMeta meta = e.getCurrentItem().getItemMeta();

        if (dataConf.getBoolean(role.toString() + "." + alert.toString())) {
            e.getCurrentItem().setType(Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Enabled.material")));
            e.getCurrentItem().setDurability(new Short(config.getConfig().getString("Faction-Alerts.gui.Enabled.damage")));
            meta.setLore(config.getConfig().getStringList("Faction-Alerts.gui.Enabled.lore"));
        } else {
            e.getCurrentItem().setType(Material.getMaterial(config.getConfig().getString("Faction-Alerts.gui.Disabled.material")));
            e.getCurrentItem().setDurability(new Short(config.getConfig().getString("Faction-Alerts.gui.Disabled.damage")));
            meta.setLore(config.getConfig().getStringList("Faction-Alerts.gui.Disabled.lore"));
        }
        e.getCurrentItem().setItemMeta(meta);
    }


}
