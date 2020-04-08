package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.potion.PotionType;

public class PotionStackerPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);

        addPath("Command.enabled", true);
        addPath("Command.permission", "btf.stackpots.command");
        addPath("Command.no-permission-message", "&cYou dont have permission!");
        addPath("Command.no-potions", "&cYou dont have potions in your inventory!");
        addPath("Command.potions-stacked", "&aYou stacked all of your potions!");
        String msg = "Messages.";
        addPath("stack-only-drinkable", true);


        String config = "Configuration.";

        addPath(config + "master-permission", "btf.stack.all");

        for (PotionType potionType : PotionType.values()) {
            addPath(config + potionType.name() + ".stack-size", 64);
            addPath(config + potionType.name() + ".permission", "btf." + potionType.name() + ".stack");
        }


    }
}
