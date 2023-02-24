package viewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private Connection connection;

    public DatabaseConnector(String fileName) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
    }

    public Connection getConnection() {
        return connection;
    }
}
