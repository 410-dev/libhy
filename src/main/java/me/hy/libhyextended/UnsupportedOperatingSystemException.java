package me.hy.libhyextended;

public class UnsupportedOperatingSystemException extends Exception {
    public UnsupportedOperatingSystemException(String message) {
        super("Unsupported operating system: " + message);
    }
}
