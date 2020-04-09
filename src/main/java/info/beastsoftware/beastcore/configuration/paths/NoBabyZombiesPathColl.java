package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NoBabyZombiesPathColl extends PathColl {


    public NoBabyZombiesPathColl() {
        super();
    }

    @Override
    public void init() {
        this.addPath("enabled", true);
    }
}
