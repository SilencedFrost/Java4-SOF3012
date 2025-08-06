package com.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String DEFAULT_CONFIG_FILE = "database.properties";
    private static final String EMAIL_CONFIG_FILE = "email.properties";
    private static boolean isDatabaseLoaded = false;
    private static boolean isEmailLoaded = false;
    private static Properties emailProperties;

    public static void loadDatabaseConfig() {
        loadDatabaseConfig(DEFAULT_CONFIG_FILE);
    }

    public static void loadDatabaseConfig(String configFileName) {
        if (isDatabaseLoaded) {
            return;
        }

        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found: " + configFileName);
            }

            Properties prop = new Properties();
            prop.load(input);

            setSystemPropertyIfExists(prop, "db.url");
            setSystemPropertyIfExists(prop, "db.user");
            setSystemPropertyIfExists(prop, "db.password");
            setSystemPropertyIfExists(prop, "db.driver");

            isDatabaseLoaded = true;
            System.out.println("Database configuration loaded successfully from: " + configFileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration from: " + configFileName, e);
        }
    }

    public static void loadEmailConfig() {
        loadEmailConfig(EMAIL_CONFIG_FILE);
    }

    public static void loadEmailConfig(String configFileName) {
        if (isEmailLoaded) {
            return;
        }

        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found: " + configFileName);
            }

            emailProperties = new Properties();
            emailProperties.load(input);

            isEmailLoaded = true;
            System.out.println("Email configuration loaded successfully from: " + configFileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load email configuration from: " + configFileName, e);
        }
    }

    public static String getEmailProperty(String key) {
        if (!isEmailLoaded) {
            loadEmailConfig();
        }
        return emailProperties.getProperty(key);
    }

    public static Properties getEmailProperties() {
        if (!isEmailLoaded) {
            loadEmailConfig();
        }
        return emailProperties;
    }

    public static String getEmailUsername() {
        return getEmailProperty("email.username");
    }

    public static String getEmailPassword() {
        return getEmailProperty("email.password");
    }

    public static String getSmtpHost() {
        return getEmailProperty("smtp.host");
    }

    public static String getSmtpPort() {
        return getEmailProperty("smtp.port");
    }

    public static String getSmtpAuth() {
        return getEmailProperty("smtp.auth");
    }

    public static String getSmtpStartTls() {
        return getEmailProperty("smtp.starttls.enable");
    }

    public static String getSmtpSslTrust() {
        return getEmailProperty("smtp.ssl.trust");
    }

    private static void setSystemPropertyIfExists(Properties prop, String key) {
        String value = prop.getProperty(key);
        if (value != null && !value.trim().isEmpty()) {
            System.setProperty(key, value);
        }
    }
}