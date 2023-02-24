package viewer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableComboBox extends JComboBox<String> {

    public TableComboBox() {}

    public void initialize(Connection connection) throws SQLException {
        removeAllItems();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'");
        while (resultSet.next()) {
            addItem(resultSet.getString("name"));
        }
    }
}

