package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private final Properties properties;

    public PropertyReader(String resourceName) {
        properties = load(resourceName);
    }

    private Properties load(String resourceName) {
        Properties loaded = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Missing configuration resource: " + resourceName);
            }
            loaded.load(input);
            return loaded;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load configuration resource: " + resourceName,
                    exception);
        }
    }

    public String getRequiredProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Missing required configuration property: " + key);
        }
        return value.trim();
    }
}
