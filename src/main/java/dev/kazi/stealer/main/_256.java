package dev.kazi.stealer.main;

import dev.kazi.stealer.node.NodeManager;

public enum _256 {

    INSTANCE;
    
    public NodeManager nodeManager;
    
    public void initialize() {
        this.nodeManager = new NodeManager();
    }
    
    public void launchNodes() throws InterruptedException {
        this.nodeManager.launch();
    }
}
