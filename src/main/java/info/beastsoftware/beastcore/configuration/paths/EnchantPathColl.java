package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantPathColl extends PathColl {
    @Override
    public void init() {


        addPath("enabled", true);


        String commandPath = "Commands.";

        addPath(commandPath + "Enchant.command-ussage", "&eCommand ussage: &7/enchant <enchant_name> <level>");
        addPath(commandPath + "Enchant.permission", "btf.enchant.command");
        addPath(commandPath + "Enchant.no-permission-msg", "&c(!) &4You dont have permission to enchant items!");
        addPath(commandPath + "Enchant.you-are-not-a-player", "&c(!) &4You must be a player to use this command!");
        addPath(commandPath + "Enchant.you-are-not-holding-anything", "&c(!) &4You dont have anything in your hand!");
        addPath(commandPath + "Enchant.invalid-enchant", "&c(!) &4The enchantment &c{enchant_name} &4does not exist!");
        addPath(commandPath + "Enchant.available-enchants-format-message", "&7- &e{enchant_name}");
        addPath(commandPath + "Enchant.invalid-level", "&c(!) &4Introduce a valid enchant level!");
        addPath(commandPath + "Enchant.item-enchanted", "&eYou enchanted your item with &a{enchant_name} &elevel &a{level}");

        addPath(commandPath + "Disenchant.enabled", true);
        addPath(commandPath + "Disenchant.permission", "btf.disenchant.command");
        addPath(commandPath + "Disenchant.permission-all", "btf.disenchantall.command");
        addPath(commandPath + "Disenchant.no-permission-msg", "&c(!) &4You dont have permission to disenchant items!");
        List<String> ussages = new ArrayList<>();
        ussages.add("&eRemove all enchants from item: &7/disenchant all");
        ussages.add("&eRemove certain enchant from item: &7/disenchant <enchant_name>");
        addPath(commandPath + "Disenchant.command-ussage", ussages);
        addPath(commandPath + "Disenchant.invalid-enchant", "&c(!) &4The item you are holding does not have that enchant! Here a list of enchants that the item has:");
        addPath(commandPath + "Disenchant.available-enchants-format-message", "&7- &e{enchant_name}");
        addPath(commandPath + "Disenchant.you-are-not-a-player", "&c(!) &4You must be a player to use this command!");
        addPath(commandPath + "Disenchant.you-are-not-holding-anything", "&c(!) &4You dont have anything in your hand!");
        addPath(commandPath + "Disenchant.the-item-has-no-enchant", "&c(!) &4The item you are holding has no enchants!");
        addPath(commandPath + "Disenchant.item-disenchanted", "&eYou disenchanted your item!");
        addPath(commandPath + "Disenchant.give-book", true);


        List<String> chestEnchants = new ArrayList<>();

        chestEnchants.add(Enchantment.PROTECTION_FIRE.getName());
        chestEnchants.add(Enchantment.PROTECTION_ENVIRONMENTAL.getName());

        List<String> swordEnchants = new ArrayList<>();
        swordEnchants.add(Enchantment.DAMAGE_ALL.getName());
        swordEnchants.add(Enchantment.DAMAGE_ARTHROPODS.getName());

        addPath("click-enchant-permission", "btf.clickenchant");
        addPath("click-enchantments-allowed.DIAMOND_SWORD", swordEnchants);
        addPath("click-enchantments-allowed.DIAMOND_CHESTPLATE", chestEnchants);
        addPath("all-enchants-must-be-allowed-in-tool", "&cYou cant use this book on this item, there are enchants that are not allowed for it!");
    }
}
