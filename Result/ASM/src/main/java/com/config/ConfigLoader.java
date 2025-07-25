package com.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String DEFAULT_CONFIG_FILE = "database.properties";
    private static boolean isLoaded = false;

    public static void loadDatabaseConfig() {
        loadDatabaseConfig(DEFAULT_CONFIG_FILE);
    }

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

    private static void setSystemPropertyIfExists(Properties prop, String key) {
        String value = prop.getProperty(key);
        if (value != null && !value.trim().isEmpty()) {
            System.setProperty(key, value);
        }
    }
}