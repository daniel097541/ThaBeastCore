package info.beastsoftware.beastcore.entity;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.api.IBeastCoreAPI;
import info.beastsoftware.hookcore.entity.BeastFaction;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.logging.Logger;

public interface APIAccessor {


    default IBeastCoreAPI API(){
        return BeastCore.getInstance().getApi();
    }

    default BeastCore getBeastCore(){
        return BeastCore.getInstance();
    }

    default Logger getLogger(){
        return this.getBeastCore().getLogger();
    }

    default BeastPlayer getPlayer(Player player){
        return this.getBeastCore().getPlayerService().getFromUUID(player.getUniqueId());
    }

    default BeastFaction getAtLocation(Location location){
        return this.getBeastCore().getFactionsService().getAtLocation(location);
    }

    default Set<BeastFaction> getAllFactions(){
        return this.getBeastCore().getFactionsService().getAll();
    }

    default boolean isFactionsHooked(){
        return this.getBeastCore().getFactionsService().isHooked();
    }
}
