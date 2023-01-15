package me.hy.libhyextended.objects.exception;

public class UndefinedSQLKeyException extends RuntimeException {
    public UndefinedSQLKeyException(String key) {
        super("The required SQL key " + key + " is undefined.");
    }
}
