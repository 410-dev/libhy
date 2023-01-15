package me.hy.libhyextended.objects.exception;

public class UndefinedSQLKeyException extends Exception {
    public UndefinedSQLKeyException(String key) {
        super("The required SQL key " + key + " is undefined.");
    }
}
