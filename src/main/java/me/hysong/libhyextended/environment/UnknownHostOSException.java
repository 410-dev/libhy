package me.hysong.libhyextended.environment;

public class UnknownHostOSException extends RuntimeException {
    public UnknownHostOSException(String osName) {
        super("Unknown host OS: " + osName);
    }
}
