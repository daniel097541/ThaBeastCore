package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class PlaceholdersPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);


        String placeholders = "Placeholders.";

        addPath(placeholders + "Gapple-Cooldown.Running", "{time} &eSeconds");
        addPath(placeholders + "Gapple-Cooldown.Finished", "&aReady!");

        addPath(placeholders + "Pearl-Cooldown.Running", "{time} &eSeconds");
        addPath(placeholders + "Pearl-Cooldown.Finished", "&aReady!");

        addPath(placeholders + "Combat.Running", "{time} &eSeconds");
        addPath(placeholders + "Combat.Finished", "&aOut of combat!");

        addPath(placeholders + "Grace.Disabled", "&cDisabled");
    }
}
