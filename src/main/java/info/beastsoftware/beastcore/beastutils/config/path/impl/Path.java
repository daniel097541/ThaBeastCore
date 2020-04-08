package info.beastsoftware.beastcore.beastutils.config.path.impl;

import info.beastsoftware.beastcore.beastutils.config.path.IPath;

public class Path implements IPath {


    private String path;
    private Object defaultValue;


    Path(String path, Object defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}

