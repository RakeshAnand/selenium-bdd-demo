package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties props = new Properties();

    static {
        load("config.properties");
        load("secrets.properties"); // optional, ignored if the file doesn't exist
    }

    private ConfigReader() {
    }

    private static void load(String fileName) {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    /**
     * Lookup order: -D system property, then environment variable
     * (app.username becomes APP_USERNAME), then the properties files.
     */
    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key.toUpperCase().replace('.', '_'));
        }
        if (value == null) {
            value = props.getProperty(key);
        }
        if (value == null) {
            throw new IllegalStateException("Missing config value for key: " + key);
        }
        return value.trim();
    }
}