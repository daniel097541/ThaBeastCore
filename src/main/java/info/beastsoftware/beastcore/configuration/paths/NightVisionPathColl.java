package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NightVisionPathColl extends PathColl {


    @Override
    public void init() {
        this.addPath("enabled", true);
        this.addPath("permission", "bfc.nv.command");
        this.addPath("no-permission-message", "&cYou dont have permission use night vision command!");
        this.addPath("enabled-message", "&eNight vision toggled on!");
        this.addPath("disabled-message", "&cNight vision toggled off!");
    }
}
