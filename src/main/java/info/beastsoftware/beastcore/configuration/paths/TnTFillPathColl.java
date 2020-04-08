package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class TnTFillPathColl extends PathColl {

    public TnTFillPathColl() {
        super();
    }

    @Override
    public void init() {


        addPath("TnT-Fill.enabled", true);
        addPath("TnT-Unfill.enabled", true);



        final String PATHUNFILL = "TnT-Unfill.command.";
        final String UNFILLSETTINGS = "TnT-Unfill.Settings.";
        List<String> formats = new ArrayList<>();

        formats.add("&dDefault radius command: &e/tntfill <amount>");
        formats.add("&dCustom radius command: &e/tntfill &a<radius> &a<amount>");
        formats.add("&dFull fill: &e/tntfill &a<radius> &afull");


        String pathCommand = "TnT-Fill.command.";
        String pathSettings = "TnT-Fill.Settings.";

        addPath(CommandType.TNTFILL.getEnabledPath(), true);

        addPath(pathCommand + "formats", formats);
        addPath(pathCommand + "permission", "btf.tntfill.use");
        addPath(pathCommand + "full-fill-permission", "btf.fullfill.use");
        addPath(pathCommand + "custom-radius-fill-permission", "btf.customradiusfill.use");
        addPath(pathCommand + "no-permission-msg", "&c(!) &4You dont have permission!");
        addPath(pathCommand + "invalid-radius", "&c(!) &4Introduce a valid number for the radius!");
        addPath(pathCommand + "invalid-amount", "&c(!) &4Introduce a valid amount of TnT!");
        addPath(pathCommand + "too-much-tnt", "&c(!) &4The maximun TNT allowed is &e576 &4!");
        addPath(pathCommand + "no-tnt-in-inventory", "&c(!) &4You dont have any TNT in your inventory!");
        addPath(pathCommand + "max-radius-reached", "&c(!) &4The max radius allowed is &e{max_radius}!");
        addPath(pathCommand + "max-radius-bypass-permission", "btf.tntfill.maxradius.bypass");
        addPath(pathCommand + "no-near-dispensers", "&4(!) &cThere are no near dispensers!");
        addPath(pathCommand + "filled-message", "&aYou have filled &e{number} &adispensers!");

        addPath(pathSettings + "max-radius", 100);
        addPath(pathSettings + "default-fill-radius", 16);

        addPath(CommandType.TNTUNFILL.getEnabledPath(), true);
        addPath(PATHUNFILL + "permission", "btf.tntunfill.use");
        addPath(PATHUNFILL + "no-permission-msg", "&c(!) &4You dont have permission to do that!");
        addPath(PATHUNFILL + "full-unfill-permission", "btf.tntfullunfill.use");
        addPath(PATHUNFILL + "custom-radius-unfill-permission", "btf.customradius.tntunfill");
        addPath(PATHUNFILL + "invalid-radius", "&c(!) &4Introduce a valid number for the radius!");
        addPath(PATHUNFILL + "invalid-amount", "&c(!) &4Introduce a valid amount of TnT!");
        addPath(PATHUNFILL + "no-near-dispensers", "&4(!) &cThere are no near dispensers!");
        addPath(PATHUNFILL + "max-radius-reached", "&c(!) &4The max radius allowed is &e{max_radius}!");
        addPath(PATHUNFILL + "max-radius-bypass-permission", "btf.tntfill.maxradius.bypass");
        addPath(PATHUNFILL + "unfilled-message", "&aYou have unfilled &e{number} &adispensers!");
        addPath(PATHUNFILL + "no-dispensers-with-tnt", "&cThere is no dispenser with tnt in the radius!");

        List<String> unfillFormats = new ArrayList<>();
        unfillFormats.add("&eUssage default radius: &7/tntunfill <amount>");
        unfillFormats.add("&eUssage custom radius: &7/tntunfill <radius> <amount>");
        unfillFormats.add("&eUssage admin mode: &7/tntunfill all <radius>");

        addPath(PATHUNFILL + "formats", unfillFormats);

        addPath(UNFILLSETTINGS + "max-radius", 100);
        addPath(UNFILLSETTINGS + "default-unfill-radius", 16);
        addPath(UNFILLSETTINGS + "factions-mode", false);
    }
}
