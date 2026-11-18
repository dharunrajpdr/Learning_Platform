package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String PROPERTIES_RESOURCE = "db.properties";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        PropertyReader reader = new PropertyReader(PROPERTIES_RESOURCE);
        String url = reader.getRequiredProperty("db.url");
        String username = reader.getRequiredProperty("db.username");
        String password = reader.getRequiredProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }
}
