package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class LootProtectionPathColl extends PathColl {


    @Override
    public void init() {


        this.addPath("enabled", true);
        this.addPath("protection-time", 30);
        this.addPath("bypass-permission", "bfc.lootprotect.bypass");
        this.addPath("message-loot-protected", "&dThe loot of the player that you killed has been protected for: &e{time} &7seconds!");
        this.addPath("message-loot-protection-ended", "&eThe loot is not protected anymore!");

    }
}
