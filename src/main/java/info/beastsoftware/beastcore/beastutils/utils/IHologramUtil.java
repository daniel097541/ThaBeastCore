package info.beastsoftware.beastcore.beastutils.utils;

import com.gmail.filoghost.holographicdisplays.disk.HologramDatabase;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import com.gmail.filoghost.holographicdisplays.object.NamedHologramManager;
import org.bukkit.Location;

import java.util.List;

public class IHologramUtil {

    public static void createHologram(Location location, List<String> lines, String name, int id) {
        name = name.toLowerCase();
        NamedHologram hologram = new NamedHologram(location, name + id);
        NamedHologramManager.addHologram(hologram);
        for (String line : lines)
            hologram.appendTextLine(line);
        hologram.refreshAll();
        HologramDatabase.saveHologram(hologram);
        HologramDatabase.trySaveToDisk();
    }


    public static void removeHologram(String name, int id) {
        name = name.toLowerCase();
        NamedHologram hologram = NamedHologramManager.getHologram(name + id);
        if (hologram == null) return;
        hologram.delete();
        NamedHologramManager.removeHologram(hologram);
        HologramDatabase.deleteHologram(hologram.getName());
        HologramDatabase.trySaveToDisk();
    }


    public static void editHologramLine(String name, int id, int line, String newLine) {
        String identificator = name.toLowerCase() + id;
        NamedHologram hologram = NamedHologramManager.getHologram(identificator);

        if (hologram == null) return;
        hologram.removeLine(line);
        hologram.insertTextLine(line, newLine);
    }

}
