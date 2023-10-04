package me.hysong.libhyextended.utils;

import java.io.FileOutputStream;
import java.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class FileBase64 {
    public static String fileToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileContent = new byte[(int) file.length()];
        fis.read(fileContent);
        fis.close();
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static void base64ToFile(String base64Content, String destinationPath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
        FileOutputStream fos = new FileOutputStream(new File(destinationPath));
        fos.write(decodedBytes);
        fos.close();
    }
}
