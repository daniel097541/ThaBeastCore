package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoePathColl  extends PathColl {
    @Override
    public void init() {
        addPath("enabled", true);
        addPath("item.material", "DIAMOND_HOE");
        addPath("item.name", "&eHarvester hoe");
        List<String> lore = new ArrayList<>();
        lore.add("&dPuts sugarcane in your inventory!");
        addPath("item.lore", lore);
        addPath("permission", "bfc.harvesterhoes.use");
        addPath("messages.received-hoe", "&dYour received a &eHarvester hoe &d!");
        addPath("command.no-permission-message", "&cNo permission to use this command!");
        addPath("command.permission", "bfc.harvesterhoes.give");
        addPath("command.player-offline", "&cThe player &7{player} &cis offline!");
        addPath("command.success-message", "&aYou gave to &e{player} &a a harvester hoe!");
        addPath("command.format", "&dCommand ussage: &e/harvesterhoes give <player>");
    }
}
