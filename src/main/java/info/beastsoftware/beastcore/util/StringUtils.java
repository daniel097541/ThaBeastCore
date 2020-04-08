package info.beastsoftware.beastcore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StringUtils implements IStringUtil {

    public String replacePlaceholder(String message, String placeholder, String replacement) {
        if (message.contains(placeholder))
            message = message.replace(placeholder, replacement);
        return message;
    }


    public List<String> translateLore(List<String> lore) {
        List<String> loreTranslated = new ArrayList<>();

        for (String line : lore) {
            line = ChatColor.translateAlternateColorCodes('&', line);
            loreTranslated.add(line);
        }

        return loreTranslated;
    }

    public String formatCooldown(int seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        return day + "d " + hours + "h " + minute + "m " + second + "s";
    }

    public List<String> replacePlaceholder(List<String> lore, String placeholder, String replacement) {
        List<String> replaced = new ArrayList<>();
        for (String line : lore) {
            String newLine = replacePlaceholder(line, placeholder, replacement);
            replaced.add(newLine);
        }
        return replaced;
    }
}
