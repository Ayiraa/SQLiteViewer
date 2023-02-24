package viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class SearchBar extends JPanel {

    private Connection connection;
    private final TableComboBox tablesComboBox;
    private final QueryTextArea queryTextArea;

    public SearchBar() {
        setLayout(new BorderLayout(10, 10)); // add 10 pixels of horizontal and vertical padding

        // Add file name text field and open button to the top
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        JTextField fileNameTextField = new JTextField();
        fileNameTextField.setName("FileNameTextField");
        topPanel.add(fileNameTextField, BorderLayout.CENTER);
        JButton openFileButton = new JButton("Open");
        openFileButton.setName("OpenFileButton");

        tablesComboBox = new TableComboBox();
        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) tablesComboBox.getSelectedItem();
                String query = "SELECT * FROM " + selectedTable + ";";
                queryTextArea.setQuery(query);
            }
        });
        add(tablesComboBox, BorderLayout.CENTER);

        // Add query text area and execute button to the middle of the panel
        JPanel middlePanel = new JPanel(new BorderLayout(10, 10));
        middlePanel.add(tablesComboBox, BorderLayout.NORTH);
        queryTextArea = new QueryTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(queryTextArea); // add a scroll pane to enable scrolling for long text
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        JButton executeQueryButton = new JButton("Execute");
        executeQueryButton.setName("ExecuteQueryButton");
        executeQueryButton.setEnabled(false);

        middlePanel.add(executeQueryButton, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);


        // Add padding around the edges of the app window
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        openFileButton.addActionListener(e -> {
            String fileName = fileNameTextField.getText();
            File file = new File(fileName);
            if (file.exists()) {
                try {
                    connection = new DatabaseConnector(fileName).getConnection();
                    tablesComboBox.initialize(connection);
                    tablesComboBox.setEnabled(true);
                    queryTextArea.setEnabled(true);
                    executeQueryButton.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(new Frame(), "SQL error: " + ex.getMessage());

                }
            }else {
                JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
                tablesComboBox.setEnabled(false);
                queryTextArea.setEnabled(false);
                executeQueryButton.setEnabled(false);
            }
        });
        // Add result table below the query text area
        JTable resultTable = new JTable();
        resultTable.setName("Table");
        resultTable.setFillsViewportHeight(true); // fill the available vertical space
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        resultScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(resultScrollPane, BorderLayout.SOUTH);

        executeQueryButton.addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(new Frame(), "Please open a database file first!");
                return;
            }

            String query = queryTextArea.getQuery();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(new Frame(), "Query field is empty!");
                return;
            }

            QueryExecutor.executeQuery(connection, query, resultTable);
        });
        topPanel.add(openFileButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);


    }
}

