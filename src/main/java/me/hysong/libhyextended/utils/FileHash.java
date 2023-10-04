package me.hysong.libhyextended.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;

public class FileHash {
    public static String hash256(String path) throws NoSuchAlgorithmException, IOException {
        return hashFile(path, "SHA-256");
    }

    public static String hash512(String path) throws NoSuchAlgorithmException, IOException {
        return hashFile(path, "SHA-512");
    }

    public static String hashFile(String path, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        Path filePath = Paths.get(path);

        try (InputStream is = Files.newInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int read;

            while ((read = is.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }

        return byteArray2Hex(digest.digest());
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
