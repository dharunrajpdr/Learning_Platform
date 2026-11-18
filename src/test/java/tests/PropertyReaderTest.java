package tests;

import org.junit.jupiter.api.Test;
import util.PropertyReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyReaderTest {
    @Test
    void readsPropertiesFromClasspath() {
        PropertyReader reader = new PropertyReader("test-db.properties");

        assertEquals("jdbc:mysql://localhost:3306/test", reader.getRequiredProperty("db.url"));
        assertEquals("test_user", reader.getRequiredProperty("db.username"));
    }

    @Test
    void rejectsMissingRequiredProperty() {
        PropertyReader reader = new PropertyReader("test-db.properties");

        assertThrows(IllegalStateException.class,
                () -> reader.getRequiredProperty("db.password"));
    }

    @Test
    void rejectsMissingResource() {
        assertThrows(IllegalStateException.class,
                () -> new PropertyReader("missing.properties"));
    }
}
