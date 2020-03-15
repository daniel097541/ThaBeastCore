package info.beastsoftware.core.config;

import info.beastsoftware.core.common.PluginAccessor;
import info.beastsoftware.core.config.impl.CoreConfig;
import info.beastsoftware.core.config.impl.DataConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public interface IDataConfig extends PluginAccessor {

    Map<String, IConfig> getConfigs();

    String getFolder();

    default void load() {
        File[] files = new File(this.getFolder()).listFiles();
        if (Objects.nonNull(files)) {
            for (File file : files) {
                try {
                    String name = file.getName().replace(".yml", "");
                    IConfig config = new DataConfig(this.getFolder(), name);
                    this.add(name, config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    default IConfig get(String id) {
        return this.getConfigs().get(id);
    }

    default void remove(String id) {
        this.getConfigs().remove(id);
    }

    default void add(String id, IConfig config) {
        this.getConfigs().put(id, config);
    }

    default boolean exists(String id) {
        return this.getConfigs().containsKey(id);
    }


}
