package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class RepeaterTicksCounterPathColl extends PathColl {
    @Override
    public void init() {

        String itemPath = "Item.";
        String command = "Command.";

        addPath("enabled", true);


        addPath(itemPath + "material", Material.BLAZE_ROD.toString());
        addPath(itemPath + "name", "&dTicks counter");
        List<String> lore = new ArrayList<>();

        lore.add("&eClick on repeaters to count ticks!");

        addPath(itemPath + "lore", lore);


        addPath(command + "enabled", true);
        addPath(command + "permission", "bfc.tickcounter.comand");
        addPath(command + "no-permission-message", "&cYou dont have permission");

        String messages = "Messages.";


        addPath(messages + "received-counter", "&eYou received a tick counter! This is an idea of: &7Kieran // HelpUs#2769");
        addPath(messages + "tick-message", "&eTicks: &7{ticks}  &aGame ticks: &7{game_ticks}");
        addPath(messages + "selection-reset", "&eYour selection has been reset!");

    }
}
