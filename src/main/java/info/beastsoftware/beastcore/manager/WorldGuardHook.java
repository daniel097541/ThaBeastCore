package info.beastsoftware.beastcore.manager;


import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.Location;

import java.util.Objects;


public class WorldGuardHook implements IWorldGuardHook {

    @Override
    public boolean isPvPDisabledHere(Location loc) {

        return WGBukkit
                .getRegionManager(loc.getWorld())
                .getApplicableRegions(loc)
                .getRegions()
                .stream()
                .map(region -> region.getFlag(DefaultFlag.PVP))
                .filter(Objects::nonNull)
                .anyMatch(f -> f.equals(StateFlag.State.DENY));

    }
}
