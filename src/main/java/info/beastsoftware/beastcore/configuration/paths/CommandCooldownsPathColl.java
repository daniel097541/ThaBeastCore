package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class CommandCooldownsPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);

        addPath("command-Cooldowns.Creeper.beastCooldown", 30);
        addPath("command-Cooldowns.Creeper.bypass-permission", "creeper.beastCooldown.bypass");
        addPath("command-Cooldowns.Creeper.on-beastCooldown-message", "&cYou cant use creeper command until the beastCooldown ends: &e{cooldown_formatted}");
    }
}
