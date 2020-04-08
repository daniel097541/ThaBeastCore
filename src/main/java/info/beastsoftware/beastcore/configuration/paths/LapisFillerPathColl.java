package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class LapisFillerPathColl extends PathColl {


    @Override
    public void init() {
        addPath("enabled", true);
        addPath("permission", "btf.lapis.fill");
    }
}
