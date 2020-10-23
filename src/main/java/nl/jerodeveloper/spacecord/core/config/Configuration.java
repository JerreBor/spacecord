package nl.jerodeveloper.spacecord.core.config;

import lombok.Getter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Configuration extends PropertiesConfiguration {

    private final URL defaultConfig = getClass().getClassLoader().getResource("settings.properties");
    private final File settingsFile = new File("settings.properties");

    public void loadSettings() throws ConfigurationException, IOException {
        if (!settingsFile.exists()) {
            InputStream stream = defaultConfig.openStream();
            load(stream);
            stream.close();

            settingsFile.createNewFile();
            save(settingsFile);
        }

        load(settingsFile);
    }

}
