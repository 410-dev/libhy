package me.hysong.libhycore;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple hex encoding and decoding.
 */
public class CoreHex {

    /**
     * This reads a file and returns the hex string of the file content.
     * @param path The path to the file.
     * @return The hex string.
     * @throws IOException
     */
    public static @NotNull String readFileToHex(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * This writes a hex string to a file.
     * @param path The path to the file.
     * @param hex The hex string.
     * @throws IOException
     */
    public static void writeHexToFile(String path, String hex) throws IOException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        Files.write(Paths.get(path), bytes);

    }

    /**
     * This method converts a hex string to a string.
     * @param hex The hex string.
     * @return The string converted from hex.
     */
    public static @NotNull String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            sb.append((char) Integer.parseInt(str, 16));
        }
        return sb.toString();
    }

    /**
     * This method converts a string to a hex string.
     * @param str The string to convert.
     * @return The hex string.
     */
    public static @NotNull String stringToHex(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(String.format("%02x", (int) c));
        }
        return sb.toString();
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public CoreHex() {}

}