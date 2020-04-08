package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

import java.util.ArrayList;
import java.util.List;

public class DebuffPathColl extends PathColl {




    @Override
    public void init() {
        addPath("enabled", true);

        addPath("permission", "btf.debuff");
        addPath("no-permission-message", "&cYou dont have permission to use the debuff command!");

        addPath("debuffed", "&eYou removed the harmful potion effects from you!");




        addPath("Effects-From-Spigot-Info-Link", "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html");


        List<String> effects = new ArrayList<>();

        effects.add("HARM");
        effects.add("CONFUSION");
        effects.add("BLINDNESS");
        effects.add("POISON");
        effects.add("SLOW");
        effects.add("WEAKNESS");

        addPath("removed-effects", effects);
    }
}
