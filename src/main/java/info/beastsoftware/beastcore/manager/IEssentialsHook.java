package info.beastsoftware.beastcore.manager;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import info.beastsoftware.beastcore.entity.APIAccessor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public interface IEssentialsHook extends APIAccessor {


    default Essentials getEssentials(){
        return (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    }

    default Map<String, Location> getHomesOfPlayer(Player player) {
        User user = this.getEssentials().getUser(player);
        return user
                .getHomes()
                .stream()
                .map(h -> {
                    try {
                        Map.Entry<String, Location> homeEntry = new AbstractMap.SimpleEntry<>(h, user.getHome(h));
                        return homeEntry;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    default void removeHomeOfPlayer(Player player, String home){
        User user = this.getEssentials().getUser(player);
        try {
            user.delHome(home);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getLogger().info("Home " + home + " of player " + player.getName() + " was removed due to it was set in enemy land!");
    }



}
