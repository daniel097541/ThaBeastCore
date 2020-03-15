package info.beastsoftware.core.config.path;

import java.util.Set;

public interface ConfigPathColl {

    Set<ConfigPath> getPaths();

    default void addPath(ConfigPath path){
        this.getPaths().add(path);
    }

    void load();

}
