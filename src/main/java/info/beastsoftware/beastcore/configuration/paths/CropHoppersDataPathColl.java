package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.Alerts;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.Material;

import java.util.HashSet;

public class CropHoppersDataPathColl extends PathColl {


    public CropHoppersDataPathColl() {
        super();
    }

    @Override
    public void init() {
        this.addPath("data", new HashSet<>());
    }
}
