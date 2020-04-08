package info.beastsoftware.beastcore.util;

import java.util.List;

public interface IStringUtil {
    String replacePlaceholder(String message, String placeholder, String replacement);

    List<String> replacePlaceholder(List<String> lore, String placeholder, String replacement);

    List<String> translateLore(List<String> lore);

    String formatCooldown(int durationSeconds);
}
