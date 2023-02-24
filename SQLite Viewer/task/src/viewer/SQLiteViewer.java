package viewer;

import javax.swing.*;
import java.awt.*;

public class SQLiteViewer extends JFrame {

    public SQLiteViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        add(new SearchBar(), BorderLayout.NORTH);
        setTitle("SQLite Viewer");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
