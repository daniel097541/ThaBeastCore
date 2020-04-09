package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

import java.util.Collections;

public class ThrowableCeggsPathColl extends PathColl {

    public ThrowableCeggsPathColl() {
        super();
    }

    @Override
    public void init() {


        addPath("enabled", true);

        addPath("explosion-delay", 2);
        addPath("explosion-size", 6);

        addPath("cegg.name", "&dThrowable creeper egg");
        addPath("cegg.lore", Collections.singleton("&6Right click to throw"));

        addPath("command.permission", "btf.throwable.eggs");
        addPath("command.formats", "&6Format: &d/throwableggs give <player> <amount>");
        addPath("command.no-permission-message", "&cYou dont have permission!");
        addPath("command.given-egg", "&6You gave &a{amount} &cegg/s to &7{player}");
        addPath("command.invalid-player", "&cThe player &7{player} &cdoes not exist!");
    }

}
