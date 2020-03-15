package info.beastsoftware.core.config.impl;

import info.beastsoftware.core.config.IConfig;
import info.beastsoftware.core.config.path.ConfigPathColl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
public class CoreConfig implements IConfig {
    private YamlConfiguration yaml;
    private File file;
    private final String folderName;
    @Getter(lazy = true)
    private final String folder = this.loadFolder();
    private final String name;
    private final ConfigPathColl paths;

    public String loadFolder() {
        String dataFolder = String.valueOf(this.getPlugin().getDataFolder());
        if (Objects.nonNull(folderName)) {
            dataFolder = dataFolder + File.separator + folderName;
        }
        return dataFolder;
    }
}
