package me.hy.libhyextended.drm;

public class SharedSecretNotSetException extends RuntimeException {
    public SharedSecretNotSetException() {
        super("Shared secret not set. Please set it before using this class by using HEDRMObject.setSharedSecret()");
    }
}
