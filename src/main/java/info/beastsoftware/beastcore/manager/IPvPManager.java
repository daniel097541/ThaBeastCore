package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.entity.APIAccessor;
import org.bukkit.Location;

public interface IPvPManager extends APIAccessor {


    boolean isPvPEnabledInLocation(Location location);


}
