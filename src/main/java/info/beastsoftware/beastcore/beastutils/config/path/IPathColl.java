package info.beastsoftware.beastcore.beastutils.config.path;

import java.util.List;

public interface IPathColl {

    void addPath(String path, Object defaultValue);

    void init();

    List<IPath> getPaths();

}