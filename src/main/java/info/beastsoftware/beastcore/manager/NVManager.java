package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public interface NVManager extends APIAccessor {


    Set<BeastPlayer> players();

    default boolean isOnNv(BeastPlayer player) {
        return this.players().contains(player);
    }


    default void addToNv(BeastPlayer player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9000000, 2));
        this.players().add(player);
    }

    default void removeFromNv(BeastPlayer player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        this.players().remove(player);
    }

}
