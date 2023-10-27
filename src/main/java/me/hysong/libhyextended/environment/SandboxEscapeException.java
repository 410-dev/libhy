package me.hysong.libhyextended.environment;

public class SandboxEscapeException extends RuntimeException{
    public SandboxEscapeException() {
        super("Process tried to escape sandbox environment!");
    }
}
