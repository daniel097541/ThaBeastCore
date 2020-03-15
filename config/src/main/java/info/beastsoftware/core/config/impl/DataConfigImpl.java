package info.beastsoftware.core.config.impl;

import info.beastsoftware.core.config.IConfig;
import info.beastsoftware.core.config.IDataConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class DataConfigImpl implements IDataConfig {

    private final String folderName;
    @Getter(lazy = true)
    private final String folder = this.loadFolder();
    private final Map<String, IConfig> configs = new HashMap<>();


    private String loadFolder(){

        String dataFolder = String.valueOf(getPlugin().getDataFolder());

        if(Objects.nonNull(this.folderName)){
            dataFolder += File.separator + this.folderName;
        }

        return dataFolder;
    }
}
