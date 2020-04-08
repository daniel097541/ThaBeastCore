package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class MobHopperPathColl extends PathColl {

    public MobHopperPathColl() {
        super();
    }

    @Override
    public void init() {

        addPath("enabled", true);


        String commandPath = "Mob-Hoppers.command.";
        String itemPath = "Mob-Hoppers.Item.";
        String settingsPath = "Mob-Hoppers.Settings.";
        addPath("Mob-Hoppers.disabled-message", "&cCrop Hoppers are disabled!");


        addPath(CommandType.MOB_HOPPER.getEnabledPath(), true);
        addPath(commandPath + "permission", "bfc.mobhoppers.give");
        addPath(commandPath + "no-permission-msg", "&C(!) You dont have permission!");
        addPath(commandPath + "player-not-online", "&cThe player is not online!");
        addPath(commandPath + "no-slot", "&cThe player has not a disponible slot!");
        addPath(commandPath + "give-message", "&eYou gave &d{player} &e{number} &7mob hopper/s of &d{mob}!");
        addPath(commandPath + "recieve-message", "&eYou recieved &d{number} &eMob hopper/s! of &d{mob}");
        addPath(commandPath + "not-allowed-mob", "&c(!) &4{mob} &cis not a mob or is not in the allowed mobs list!");
        List<String> formats = new ArrayList<>();

        formats.add("&dUssage: ");
        formats.add("&7- &6/mobhoppers give <player> <mob> <number>");

        addPath(commandPath + "formats", formats);

        List<String> lore = new ArrayList<>();
        lore.add("&ePlace this hopper");
        lore.add("&eand collect all mob drops!");
        addPath(itemPath + "name", "&d{mob} hopper!");
        addPath(itemPath + "lore", lore);

        List<String> mobs = new ArrayList<>();

        mobs.add(EntityType.IRON_GOLEM.toString());
        mobs.add(EntityType.COW.toString());
        mobs.add(EntityType.ZOMBIE.toString());
        mobs.add(EntityType.PIG_ZOMBIE.toString());
        mobs.add(EntityType.SKELETON.toString());
        mobs.add(EntityType.SPIDER.toString());
        mobs.add(EntityType.BLAZE.toString());
        mobs.add("ALL");

        addPath(settingsPath + "factions-mode", false);
        addPath(settingsPath + "collect-radius-in-chunks", 2);
        addPath(settingsPath + "place-permission", "bfc.mobhoppers.place");
        addPath(settingsPath + "break-permission", "bfc.mobhoppers.break");
        addPath(settingsPath + "mob-list", mobs);

    }
}
