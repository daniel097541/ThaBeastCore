package info.beastsoftware.core.config.path.impl;

import info.beastsoftware.core.config.path.ConfigPath;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigPathImpl implements ConfigPath {
    private final String path;
    private final Object value;
}
