package info.beastsoftware.core.config.path.impl;

import info.beastsoftware.core.config.path.ConfigPath;
import info.beastsoftware.core.config.path.ConfigPathColl;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class ConfigPathCollImpl implements ConfigPathColl {
    private final Set<ConfigPath> paths = new HashSet<>();
}
