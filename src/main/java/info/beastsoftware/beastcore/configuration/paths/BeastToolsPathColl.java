package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class BeastToolsPathColl extends PathColl {

    public BeastToolsPathColl() {
        super();
    }

    @Override
    public void init() {
        List<String> lore = new ArrayList<>();
        List<String> entities = new ArrayList<>();

        lore.add("&bUse this litghtning wand");
        lore.add("&bTo strike like zeus!");

        entities.add("PLAYER");
        entities.add("CREEPER");
        entities.add("SPIDER");
        addPath("Lightning-Tools.enabled", true);
        addPath("Lightning-Tools.disabled-message", "&c(!)&4Lightning wands are disabled!");
        addPath("Lightning-Tools.Tool.Material", "BLAZE_ROD");
        addPath("Lightning-Tools.Tool.Name", "&eLightning Wand");
        addPath("Lightning-Tools.Tool.Lore", lore);
        addPath("Lightning-Tools.Tool.Allowed-Entities-List", entities);

        addPath(CommandType.LIGHTING_TOOLS.getEnabledPath(), true);

        addPath("Lightning-Tools.command.permission", "btf.lighting.give");
        addPath("Lightning-Tools.command.message", "&dThe gods of the server gave you a lighting wand! You lucky son of a *****");
        addPath("Lightning-Tools.command.no-permission", "&4(!) &cAh ah ah you didn't say the magic word!");
        addPath("Lightning-Tools.command.player-not-online", "&4(!) &cThat player is not online or does not exists!");
        addPath("Lightning-Tools.command.format", "&dUssage: &e/lt give <player> <amount>");
        addPath("Lightning-Tools.BeastCooldown.beastCooldown-time", 30);
        addPath("Lightning-Tools.BeastCooldown.on-beastCooldown-message", "&4(!) You cannot use that until the beastCooldown ends! BeastCooldown: &e{beastCooldown}");
    }

}
