package info.beastsoftware.beastcore.beastutils.utils;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.gui.entity.Button;
import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ButtonUtil {


    public static IButton createButton(IConfig config, String path, boolean toggeable, boolean enabled, String buttonRole) {

        Material material = Material.valueOf(config.getConfig().getString(path + "material"));
        String name = config.getConfig().getString(path + "name");
        List<String> lore;

        if (toggeable)
            lore = getToggeableLore(config, path, enabled);

        else
            lore = config.getConfig().getStringList(path + "lore");

        short damage = Short.valueOf(config.getConfig().getInt(path + "damage") + "");
        int position = config.getConfig().getInt(path + "position");

        return generateButton(material, name, lore, damage, position, buttonRole);
    }

    public static IButton generateButton(Material material, String name, List<String> lore, short damage, int position, String role) {
        ItemStack item = IInventoryUtil.createItem(1, material, name, lore, damage, true);
        return new Button(position, role, item);
    }


    public static List<String> getToggeableLore(IConfig config, String path, boolean enabled) {
        List<String> lore;

        if (enabled)
            lore = config.getConfig().getStringList(path + "lore-enabled");
        else lore = config.getConfig().getStringList(path + "lore-disabled");

        return lore;
    }


}
