package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NoEndermanTeleportPathColl extends PathColl {
    @Override
    public void init() {
        this.addPath("enabled", true);
    }
}
