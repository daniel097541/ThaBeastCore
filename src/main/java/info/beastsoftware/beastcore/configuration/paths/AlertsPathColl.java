package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.Alerts;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.Material;

public class AlertsPathColl extends PathColl {


    public AlertsPathColl() {
        super();
    }

    @Override
    public void init() {

        {
            addPath("Faction-Alerts.enabled", true);
            addPath("Faction-Alerts.disabled-message", "&c(!)&4 Alerts are disabled!");
            addPath(CommandType.FACTION_ALERTS.getEnabledPath(), true);
            addPath("Faction-Alerts.command.permission", "btf.falerts.command");
            addPath("Faction-Alerts.command.no-permission", "&4(!) &cYou dont have permission!");
            addPath("Faction-Alerts.command.no-faction", "&4(!) &cYou need a faction to perform this command!");
            addPath("Faction-Alerts.command.no-faction-admin", "&4(!) &cYou must be a faction admin to perform this command!");
            addPath("Faction-Alerts.messages." + Alerts.TNT_EXPLODE.toString(), "&d&lFaction-Alert &7> A &4tnt &7has explode in your faction! Location: &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.CREEPER_EXPLODE.toString(), "&d&lFaction-Alert &7> A &4Creeper &7has explode in your faction! Location: &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.CREEPER_PLACE.toString(), "&d&lFaction-Alert &7> A &4Creeper &7has been placed in your faction by: &d{player}! &7Location > &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.TNT_PLACE.toString(), "&d&lFaction-Alert &7> A &4TNT &7has been placed in your faction by: &d{player}! &7Location > &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.SPAWNER_BREAK.toString(), "&d&lFaction-Alert &7> A &4SPAWNER &7has been broken in your faction by &d{player}! &7Location: &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.SPAWNER_PLACE.toString(), "&d&lFaction-Alert &7> A &4SPAWNER &7has been placed in your faction by &d{player}! &7Location: &e{location}");
            addPath("Faction-Alerts.messages." + Alerts.PLAYER_LOGING.toString(), "&d&lFaction-Alert &7> &a{player} &7has logged in!");
            addPath("Faction-Alerts.messages." + Alerts.PLAYER_LOGOUT.toString(), "&d&lFaction-Alert &7> &a{player} &7has logged out!");
            addPath("Faction-Alerts.messages." + Alerts.DEATH_IN_COMBAT.toString(), "&d&lFaction-Alert &7> &a{player} &7has dead while in combat!");

            addPath("Faction-Alerts.gui.Title", "&dFaction Alerts!");
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_EXPLODE.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_EXPLODE.toString() + ".name", "&bCreeper Explode");
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_EXPLODE.toString() + ".position-in-gui", 21);
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_PLACE.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_PLACE.toString() + ".name", "&bCreeper Place");
            addPath("Faction-Alerts.gui." + Alerts.CREEPER_PLACE.toString() + ".position-in-gui", 22);
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_BREAK.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_BREAK.toString() + ".name", "&bSpawner Break");
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_BREAK.toString() + ".position-in-gui", 23);
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_PLACE.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_PLACE.toString() + ".name", "&bSpawner Place");
            addPath("Faction-Alerts.gui." + Alerts.SPAWNER_PLACE.toString() + ".position-in-gui", 30);
            addPath("Faction-Alerts.gui." + Alerts.TNT_EXPLODE.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.TNT_EXPLODE.toString() + ".name", "&bTNT Explode");
            addPath("Faction-Alerts.gui." + Alerts.TNT_EXPLODE.toString() + ".position-in-gui", 31);
            addPath("Faction-Alerts.gui." + Alerts.TNT_PLACE.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.TNT_PLACE.toString() + ".name", "&bTNT Place");
            addPath("Faction-Alerts.gui." + Alerts.TNT_PLACE.toString() + ".position-in-gui", 32);

            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGOUT.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGOUT.toString() + ".name", "&bPlayer logout");
            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGOUT.toString() + ".position-in-gui", 39);

            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGING.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGING.toString() + ".name", "&bPlayer login");
            addPath("Faction-Alerts.gui." + Alerts.PLAYER_LOGING.toString() + ".position-in-gui", 40);

            addPath("Faction-Alerts.gui." + Alerts.DEATH_IN_COMBAT.toString() + ".enabled", true);
            addPath("Faction-Alerts.gui." + Alerts.DEATH_IN_COMBAT.toString() + ".name", "&bDeath in combat");
            addPath("Faction-Alerts.gui." + Alerts.DEATH_IN_COMBAT.toString() + ".position-in-gui", 41);


            addPath("Faction-Alerts.gui.Enabled.material", "EMERALD");
            addPath("Faction-Alerts.gui.Disabled.material", "REDSTONE_BLOCK");
            Material spacerMat;
            try {
                spacerMat = Material.valueOf("STAINED_GLASS_PANE");
            } catch (IllegalArgumentException ignored) {
                spacerMat = Material.BLACK_STAINED_GLASS_PANE;
            }
            addPath("Faction-Alerts.gui.Spacer.material", spacerMat.toString());
            addPath("Faction-Alerts.gui.Spacer.damage", "0");
            addPath("Faction-Alerts.gui.Enabled.lore", "&aEnabled");
            addPath("Faction-Alerts.gui.Disabled.lore", "&cDisabled");
            addPath("Faction-Alerts.gui.Enabled.damage", "0");
            addPath("Faction-Alerts.gui.Disabled.damage", "0");

            /// COLEADERS BUTTON
            addPath("Faction-Alerts.gui.Buttons.Coleader.button-name", "&dColeaders");
            addPath("Faction-Alerts.gui.Buttons.Coleader.button-lore", "&6Enabled alerts for Coleaders!");
            addPath("Faction-Alerts.gui.Buttons.Coleader.button-material", "DIAMOND_AXE");
            addPath("Faction-Alerts.gui.Buttons.Coleader.button-damage-id", "0");
            addPath("Faction-Alerts.gui.Buttons.Coleader.position-in-gui", 21);
            /// ADMINS BUTTON
            addPath("Faction-Alerts.gui.Buttons.Admin.button-name", "&dAdmins");
            addPath("Faction-Alerts.gui.Buttons.Admin.button-lore", "&6Enabled alerts for Admins!");
            addPath("Faction-Alerts.gui.Buttons.Admin.button-material", "DIAMOND");
            addPath("Faction-Alerts.gui.Buttons.Admin.button-damage-id", "0");
            addPath("Faction-Alerts.gui.Buttons.Admin.position-in-gui", 13);

            /// MODS BUTTON
            addPath("Faction-Alerts.gui.Buttons.Moderator.button-name", "&BMods");
            addPath("Faction-Alerts.gui.Buttons.Moderator.button-lore", "&6Enabled alerts for Mods!");
            addPath("Faction-Alerts.gui.Buttons.Moderator.button-material", "EMERALD");
            addPath("Faction-Alerts.gui.Buttons.Moderator.button-damage-id", "0");
            addPath("Faction-Alerts.gui.Buttons.Moderator.position-in-gui", 31);

            /// MEMBERS BUTTON
            addPath("Faction-Alerts.gui.Buttons.Member.button-name", "&DMembers");
            addPath("Faction-Alerts.gui.Buttons.Member.button-lore", "&6Enabled alerts for members!");
            addPath("Faction-Alerts.gui.Buttons.Member.button-material", Material.IRON_INGOT.toString());
            addPath("Faction-Alerts.gui.Buttons.Member.button-damage-id", "0");
            addPath("Faction-Alerts.gui.Buttons.Member.position-in-gui", 23);

            /// BACK BUTTON
            addPath("Faction-Alerts.gui.Buttons.Back.button-name", "&EBack");
            addPath("Faction-Alerts.gui.Buttons.Back.button-lore", "&dReturn to main menu!");
            addPath("Faction-Alerts.gui.Buttons.Back.button-material", "ARROW");
            addPath("Faction-Alerts.gui.Buttons.Back.button-damage-id", "0");
            addPath("Faction-Alerts.gui.Buttons.Back.position-in-gui", 0);

        }
    }
}
