package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NoEnemyHomesPathColl extends PathColl {
    @Override
    public void init() {
        addPath("enabled", true);
        addPath("allow-homes-in-ally-land", true);
        addPath("bypass-permission", "bfc.enemy.homes");
    }
}
