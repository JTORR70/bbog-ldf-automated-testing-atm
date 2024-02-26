package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFile {

    Properties properties;
    private static final Logger LOGGER = Logger.getLogger("ConfigFile");
    String FILE_PATH = "config//configuration.properties";


    public ConfigFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            properties = new Properties();
            properties.load(reader);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.getMessage());
            throw new NullPointerException("configuration.properties not found at " + FILE_PATH);
        }
    }

    public String getProperty(String property) {
        property = properties.getProperty(property);
        if (property != null) {
            return property;
        } else {
            throw new NullPointerException(property + " not specified in the Configuration.properties file.");
        }
    }
}

