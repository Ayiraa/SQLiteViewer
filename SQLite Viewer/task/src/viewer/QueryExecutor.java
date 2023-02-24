package viewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class QueryExecutor {
    public static void executeQuery(Connection connection, String query, JTable resultTable) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
            resultTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
