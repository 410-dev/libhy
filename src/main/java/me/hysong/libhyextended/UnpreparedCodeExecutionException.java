package me.hysong.libhyextended;

public class UnpreparedCodeExecutionException extends RuntimeException {
    public UnpreparedCodeExecutionException(String errorMethod, String requiredMethod) {
        super("The method " + errorMethod + " is not prepared to be executed. Please call " + requiredMethod + " first.");
    }
}
