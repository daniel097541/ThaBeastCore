package info.beastsoftware.core.config;

import info.beastsoftware.core.common.PluginAccessor;
import info.beastsoftware.core.config.path.ConfigPath;
import info.beastsoftware.core.config.path.ConfigPathColl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public interface IConfig extends PluginAccessor {

    YamlConfiguration getYaml();

    File getFile();

    void setYaml(YamlConfiguration yaml);

    void setFile(File file);

    String getName();

    String getFolder();

    ConfigPathColl getPaths();

    default void load() throws IOException {
        // initialize
        File file = new File(this.getFolder(), this.getName());
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        // load
        this.getPaths().load();
        for(ConfigPath path : this.getPaths().getPaths()){
            if(Objects.isNull(yamlConfiguration.get(path.getPath()))){
                yamlConfiguration.set(path.getPath(), path.getValue());
            }
        }

        // save
        yamlConfiguration.save(file);
        this.setFile(file);
        this.setYaml(yamlConfiguration);
    }

    default boolean delete(){
        return this.getFile().delete();
    }


}
