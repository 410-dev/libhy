package me.hysong.libhyextended.utils;

public class Run {
    public static void async(Runnable runnable) {
        new Thread(runnable).start();
    }
}
