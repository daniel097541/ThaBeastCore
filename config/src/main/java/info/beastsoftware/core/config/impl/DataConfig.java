package info.beastsoftware.core.config.impl;

import info.beastsoftware.core.config.path.impl.ConfigPathCollImpl;
import info.beastsoftware.core.config.path.impl.ConfigPathImpl;

import java.io.IOException;

public class DataConfig extends CoreConfig {
    public DataConfig(String folderName, String name) throws IOException {
        super(folderName, name, new ConfigPathCollImpl() {
            @Override
            public void load() {
                this.addPath(new ConfigPathImpl("data", ""));
            }
        });

        this.load();
    }
}
