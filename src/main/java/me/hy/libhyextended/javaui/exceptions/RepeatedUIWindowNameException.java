package me.hy.libhyextended.javaui.exceptions;

public class RepeatedUIWindowNameException extends RuntimeException {
    public RepeatedUIWindowNameException(String message) {
        super("Repeated window name: " + message);
    }
}
