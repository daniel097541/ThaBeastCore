package info.beastsoftware.beastcore.manager;


import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public interface HomesManager extends APIAccessor {

    IHookManager getHookManager();

    default void checkHomes(Player player, boolean allowAlly) {

        if (this.getHookManager().isEssentialsHooked() && this.getHookManager().isFactionsHooked()) {
            IEssentialsHook essentialsHook = this.getHookManager().getEssentialsHook();

            Map<String, Location> homes = essentialsHook.getHomesOfPlayer(player);

            for (Map.Entry<String, Location> homeEntry : homes.entrySet()) {

                String homeName = homeEntry.getKey();
                Location homeLocation = homeEntry.getValue();

                BeastPlayer beastPlayer = this.getPlayer(player);

                boolean isEnemy = beastPlayer.getMyFaction().isEnemy(this.getAtLocation(homeLocation));

                if (isEnemy) {
                    essentialsHook.removeHomeOfPlayer(player, homeName);
                }

            }
        }
    }


}
