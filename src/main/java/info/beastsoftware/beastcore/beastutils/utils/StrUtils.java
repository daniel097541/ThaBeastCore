package info.beastsoftware.beastcore.beastutils.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StrUtils {


    public static void sms(Player player, String message) {
        player.sendMessage(translate(message));
    }

    public static void sms(CommandSender player, String message) {
        player.sendMessage(translate(message));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translateLore(List<String> lore) {
        List<String> newLore = new ArrayList<>();
        for (String line : lore)
            newLore.add(translate(line));
        return newLore;
    }

    public static void mms(List<String> messages, Player player) {
        for (String message : messages)
            sms(player, message);
    }


    public static void mms(List<String> messages, CommandSender player) {
        for (String message : messages)
            sms(player, message);
    }

    public static String replacePlaceholder(String message, String placeholder, String replacement) {
        if (message.contains(placeholder))
            message = message.replace(placeholder, replacement);
        return message;
    }


    public static List<String> replacePlaceholder(List<String> lore, String placeholder, String replacement) {
        List<String> newLore = new ArrayList<>();
        for (String line : lore)
            newLore.add(replacePlaceholder(line, placeholder, replacement));
        return newLore;
    }

}
