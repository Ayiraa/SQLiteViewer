package viewer;

import javax.swing.*;

public class QueryTextArea extends JTextArea {
    public QueryTextArea() {
        super(10,100);
        setLineWrap(true);
        setWrapStyleWord(true);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void setQuery(String query) {
        setText(query);
    }

    public String getQuery() {
        return getText();
    }
}
