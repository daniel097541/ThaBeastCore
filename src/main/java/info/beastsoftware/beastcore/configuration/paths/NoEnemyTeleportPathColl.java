package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NoEnemyTeleportPathColl extends PathColl {



    @Override
    public void init() {
        addPath("enabled", true);
        addPath("message", "&7You &ccannot &7teleport to &cenemy &7factions land!");
        addPath("bypass-permission", "bfc.enemy.teleport");
    }
}
