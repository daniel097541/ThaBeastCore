package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class AnvilColorRenamePathColl extends PathColl {


    @Override
    public void init() {
        addPath("enabled", true);
        addPath("permission", "btf.anvil.color");
    }
}
