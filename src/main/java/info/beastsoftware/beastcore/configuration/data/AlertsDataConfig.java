package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import info.beastsoftware.beastcore.struct.Alerts;
import info.beastsoftware.beastcore.struct.Role;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AlertsDataConfig extends BeastDataConfig {

    public AlertsDataConfig(String folder) {
        super(folder);
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
        for (Role role : Role.values()) {
            for (Alerts alert : Alerts.values()) {
                config.set(role.toString() + "." + alert.toString(), true);
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
