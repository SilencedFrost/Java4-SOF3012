package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String DEFAULT_CONFIG_FILE = "database.properties";
    private static boolean isLoaded = false;

    /**
     * Loads database properties and sets them as system properties
     * Uses default database.properties file
     */
    public static void loadDatabaseConfig() {
        loadDatabaseConfig(DEFAULT_CONFIG_FILE);
    }

    /**
     * Loads database properties from specified file and sets them as system properties
     * @param configFileName the properties file name
     */
    public static void loadDatabaseConfig(String configFileName) {
        if (isLoaded) {
            return; // Prevent loading multiple times
        }

        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found: " + configFileName);
            }

            Properties prop = new Properties();
            prop.load(input);

            // Set system properties for JPA
            setSystemPropertyIfExists(prop, "db.url");
            setSystemPropertyIfExists(prop, "db.user");
            setSystemPropertyIfExists(prop, "db.password");
            setSystemPropertyIfExists(prop, "db.driver");

            isLoaded = true;
            System.out.println("Database configuration loaded successfully from: " + configFileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration from: " + configFileName, e);
        }
    }

    /**
     * Sets system property if the key exists in the properties file
     */
    private static void setSystemPropertyIfExists(Properties prop, String key) {
        String value = prop.getProperty(key);
        if (value != null && !value.trim().isEmpty()) {
            System.setProperty(key, value);
        }
    }

    /**
     * Gets a property value, useful for debugging
     */
    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * Checks if configuration is loaded
     */
    public static boolean isConfigLoaded() {
        return isLoaded;
    }

    /**
     * Resets the loaded flag - useful for testing
     */
    public static void reset() {
        isLoaded = false;
    }
}