package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class GracePathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);

        String GRACEPATH = "Grace-Period.";

        List<String> formats = new ArrayList<>();

        formats.add("&7---------- &8*** &dGrace &8*** &7----------");
        formats.add(" ");
        formats.add(" ");
        formats.add("&eUse &7/grace enable &eto start/resume the grace period!");
        formats.add("&eUse &7/grace disable &eto pause the grace period!");
        formats.add("&eUse &7/grace &eto see the time left!");
        formats.add("&eUse &7/grace restart &eto reset the time left of the grace to the initial time!");
        formats.add(" ");



        addPath(GRACEPATH + "formats", formats);
        addPath(GRACEPATH + "permission", "grace-command");
        addPath(GRACEPATH + "toggle-permission", "btf.grace.toggle");
        addPath(GRACEPATH + "no-permission", "&4&l(!) &cYou don´t have permission!");
        addPath(GRACEPATH + "not-enabled-message", "&4&l(!) &cGrace is not enabled!");
        addPath(GRACEPATH + "grace-enabled-message", "&aGrace period has started! Type &d/grace &ato see how much time lefts!");
        addPath(GRACEPATH + "message", "&d&lGrace &7&f> &eGrace period will end in: &d&l{time_left}");
        addPath(GRACEPATH + "already-enabled", "&eGrace period is already enabled!");
        addPath(GRACEPATH + "initial-time", 86400);
        addPath(GRACEPATH + "left", 86400);
        addPath(GRACEPATH + "running", false);
        addPath(GRACEPATH + "end-message", "&d&lGrace &e> &fGrace period has ended, now you can use TNT!");
        addPath(GRACEPATH + "Explosions.disabled", true);
        addPath(GRACEPATH + "Auto-Save-Interval", 300);

        addPath(GRACEPATH + "deny-tnt-place", true);
        addPath(GRACEPATH + "deny-tnt-place-message", "&cYou cant place tnt while in grace period!");

        addPath(CommandType.GRACE.getEnabledPath(), true);
    }
}
