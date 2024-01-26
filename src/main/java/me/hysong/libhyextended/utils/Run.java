package me.hysong.libhyextended.utils;

public class Run {
    public static void async(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void async(int delayMs, Runnable runnable) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            async(runnable);
        }).start();
    }

    public static void sync(Runnable runnable) {
        runnable.run();
    }

    public static void sync(int delayMs, Runnable runnable) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sync(runnable);
    }
}
