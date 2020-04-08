package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

public class NoRespawnScreenPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);

        addPath("Command.permission", "btf.autospawn");
        addPath("Command.no-permission-message", "&cYou dont have permission to toggle autorespawn!");

        addPath("Command.toggled-on", "&aYou toggled on autorespawn!");
        addPath("Command.toggled-off", "&cYou toggled off autorespawn!");

        addPath("disabled-players.exampleplayername", true);

    }
}
