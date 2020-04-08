package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class CombatPathColl extends PathColl {


    public CombatPathColl() {
        super();
    }

    @Override
    public void init() {
        List<String> whitelist = new ArrayList<>();
        whitelist.add("/tpyes");
        whitelist.add("/etpyes");
        whitelist.add("/tpaccept");
        whitelist.add("/etpaccept");
        whitelist.add("/tpahere");
        whitelist.add("/fix *");
        addPath("Combat-Tag.command.Permission", "bfc.combat");
        addPath("Combat-Tag.command.gui-permission", "bfc.gui.combat");
        addPath("Combat-Tag.command.No-Permission", "&c(!) &4You dont have permission to use this command!");
        addPath(CommandType.CT.getEnabledPath(), true);
        addPath("Combat-Tag.enabled", true);
        addPath("Combat-Tag.trigger-combat-with-mobs", false);
        addPath("Combat-Tag.disabled-message", "&cCombat tag is disabled!");
        addPath("Combat-Tag.duration", 20);
        addPath("Combat-Tag.message", "&d&lCombat &7>&8> &e{beastCooldown} &8seconds&8!");
        addPath("Combat-Tag.not-in-combat", "&d&lCombat &7>&8> You are not in combat!");
        addPath("Combat-Tag.bypass-permission", "btf.combat.bypass");
        addPath("Combat-Tag.denied-command", "&d&lCombat &7>&8> You can´t use that command while in combat!");
        addPath("Combat-Tag.whitelist-mode", true);
        addPath("Combat-Tag.whithelisted-commands", whitelist.toArray());
        addPath("Combat-Tag.blacklist-mode", false);
        addPath("Combat-Tag.blacklisted-commands", whitelist.toArray());
        addPath("Combat-Tag.cancel-fly", true);
        addPath("Combat-Tag.combat-started", "&d&lCombate &7>&8> You are in combat, don´t log out!");
        addPath("Combat-Tag.no-longer-in-combat", "&d&lCombat &7>&8> Now you are not in combat!");
        addPath("Combat-Tag.allow-enderpearls-in-combat", true);
        addPath("Combat-Tag.enderpearls-not-allowed-in-combat-message", "&cYou cant use ender pearls whilst you are in combat!");
        addPath("Combat-Tag.reset-on-enderpearl", true);
        addPath("Combat-Tag.disable-teleport", true);
        addPath("Combat-Tag.no-teleport-in-combat", "&cYou can´t teleport while in combat!!");

        addPath("Combat-Tag.deny-access-to-no-pvp-zones", true);
        addPath("Combat-Tag.barrier-material-for-no-pvp-zones", "STAINED_GLASS");
        addPath("Combat-Tag.barrier-damage-for-no-pvp-zones", 14);

        String guiPath = "Combat-Tag.gui.";
        String guiButtons = guiPath + "Buttons.";
        addPath(guiPath + "title", "&eCombat Tag Settings");
        addPath(guiPath + "size", 54);
        addPath(guiPath + "fill-with-spacers", true);
        addPath(guiButtons + "Toggle-Button.name", "&eToggle");
        addPath(guiButtons + "Toggle-Button.material", Material.BOOK.toString());
        addPath(guiButtons + "Toggle-Button.damage", 0);
        addPath(guiButtons + "Toggle-Button.position-in-gui", 53);
        List<String> enabledLore = new ArrayList<>();
        enabledLore.add("&aEnabled");
        addPath(guiButtons + "Toggle-Button.enabled-lore", enabledLore);
        List<String> disabledLore = new ArrayList<>();
        disabledLore.add("&cDisabled");
        addPath(guiButtons + "Toggle-Button.disabled-lore", disabledLore);

        addPath(guiButtons + "Close-Button.name", "&cClose");
        addPath(guiButtons + "Close-Button.material", Material.REDSTONE_BLOCK.toString());
        addPath(guiButtons + "Close-Button.damage", 0);
        addPath(guiButtons + "Close-Button.position-in-gui", 53);
        addPath(guiButtons + "Close-Button.lore", new ArrayList<>());

        addPath(guiButtons + "Back-Button.name", "&eBack");
        addPath(guiButtons + "Back-Button.material", Material.ARROW.toString());
        addPath(guiButtons + "Back-Button.damage", 0);
        addPath(guiButtons + "Back-Button.position-in-gui", 53);
        addPath(guiButtons + "Back-Button.lore", new ArrayList<>());

        addPath(guiButtons + "Save-Button.name", "&aSave");
        addPath(guiButtons + "Save-Button.material", Material.BOOK.toString());
        addPath(guiButtons + "Save-Button.damage", 0);
        addPath(guiButtons + "Save-Button.position-in-gui", 52);
        addPath(guiButtons + "Save-Button.lore", new ArrayList<>());

        addPath(guiButtons + "Denied-Blocks-Button.name", "&eDenied blocks");
        addPath(guiButtons + "Denied-Blocks-Button.material", Material.BEDROCK.toString());
        addPath(guiButtons + "Denied-Blocks-Button.damage", 0);
        addPath(guiButtons + "Denied-Blocks-Button.position-in-gui", 13);
        List<String> deniedLore = new ArrayList<>();
        deniedLore.add("&7Open a gui where you can place");
        deniedLore.add("&7the blocks that players wont");
        deniedLore.add("&7be able to place while in combat!");
        addPath(guiButtons + "Denied-Blocks-Button.lore", deniedLore);

        addPath(guiButtons + "Cancel-Fly-Button.name", "&eCancel Fly in combat");
        addPath(guiButtons + "Cancel-Fly-Button.material", Material.FEATHER.toString());
        addPath(guiButtons + "Cancel-Fly-Button.damage", 0);
        addPath(guiButtons + "Cancel-Fly-Button.position-in-gui", 21);
        addPath(guiButtons + "Cancel-Fly-Button.enabled-lore", enabledLore);
        addPath(guiButtons + "Cancel-Fly-Button.disabled-lore", disabledLore);


        addPath(guiButtons + "Reset-On-Pearl-Button.name", "&eReset combat tag on pearl throw");
        addPath(guiButtons + "Reset-On-Pearl-Button.material", Material.ENDER_PEARL.toString());
        addPath(guiButtons + "Reset-On-Pearl-Button.damage", 0);
        addPath(guiButtons + "Reset-On-Pearl-Button.position-in-gui", 23);
        addPath(guiButtons + "Reset-On-Pearl-Button.enabled-lore", enabledLore);
        addPath(guiButtons + "Reset-On-Pearl-Button.disabled-lore", disabledLore);


        addPath(guiButtons + "Combat-Duration-Button.name", "&eCombat duration");
        ///////////// 1.13
        Material material;
        Material material1;
        try {
            material = Material.valueOf("WATCH");
            material1 = Material.valueOf("STAINED_GLASS_PANE");
        } catch (IllegalArgumentException ignored) {
            material = Material.CLOCK;
            material1 = Material.BLACK_STAINED_GLASS_PANE;
        }
        ///////////// 1.13
        addPath(guiButtons + "Combat-Duration-Button.material", material.toString());
        addPath(guiButtons + "Combat-Duration-Button.damage", 0);
        addPath(guiButtons + "Combat-Duration-Button.position-in-gui", 31);
        List<String> durationLore = new ArrayList<>();
        durationLore.add("&eActual duration: &c{combat_duration}");
        durationLore.add("&6Increase by left click: &c{increase_by_click}");
        durationLore.add("&eDecrease by right click: &c{decrease_by_click}");
        addPath(guiButtons + "Combat-Duration-Button.lore", durationLore);
        addPath(guiButtons + "Combat-Duration-Button.increase-by-click", 5);
        addPath(guiButtons + "Combat-Duration-Button.decrease-by-click", 5);

        addPath(guiButtons + "Spacer-Button.material", material1.toString());
        addPath(guiButtons + "Spacer-Button.damage", 0);

        addPath("Combat-Tag.combat-logger-detected", "&dCombat &7>> &eThe player &b{player} &ewas killed for disconnecting in combat!");
        addPath("Combat-Tag.time-to-die-after-disconnecting", 20);
        addPath("Combat-Tag.combat-npc-name", "&eNPC of &d{player} &eTime to die: &7{time} &es");

        addPath("Combat-Tag.Blocked-Item-Message", "&cYou cant place this block while in combat!");
        addPath("Combat-Tag.Blocked-Items", new ArrayList<>().toArray());

        addPath("Combat-Tag.disable-inventories", true);
        addPath("Combat-Tag.inventories-disabled", "&cYou cannot open inventories while in combat!");

    }
}
