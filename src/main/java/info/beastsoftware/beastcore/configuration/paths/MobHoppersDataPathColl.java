package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

import java.util.HashSet;

public class MobHoppersDataPathColl extends PathColl {


    public MobHoppersDataPathColl() {
        super();
    }

    @Override
    public void init() {
        this.addPath("data", new HashSet<>());
    }
}
