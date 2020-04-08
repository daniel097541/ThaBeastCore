package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.potion.PotionEffectType;


public class PotionsPathColl extends PathColl {
    @Override
    public void init() {


        addPath("enabled", true);

        addPath("available-potion-types", "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
        String pathCmd = "Commands.Potions.";

        addPath(pathCmd + "base-permission", "btf.potion.command");
        addPath(pathCmd + "no-permission-msg", "&cYou dont have the base permission!");

        addPath(pathCmd + "format-message", "&7/pot &e{potion_name}");
        addPath(pathCmd + "potion-not-listed", "&cThe potion &7{name} &cis not allowed!");

        String path = "Commands.Potions.Pot-List." + PotionEffectType.SPEED.getName();
        addPath(path + ".permission", "btf.speed.potion");
        addPath(path + ".no-permission-msg", "&cYou dont have permission to use potion speed!");
        addPath(path + ".duration", 20);
        addPath(path + ".level", 2);
        addPath(path + ".message", "&eYou used speed potion!");

    }
}
