package me.hysong.libhyextended.utils;

public class DeveloperEnvironment {

    // Debug level - Code will execute based on this level.
    // 0 - Production
    // > 0 - Development
    public static int executeLevel = 0;

    /**
     * This method will run the given runnable function if the debug level is greater than 0.
     * Greater value means more debugging mode.
     * For example, if the debug level is set to 3, and the argument has 2, then debug will be executed.
     * If the debug level is set to 3, and the argument has 4, then debug will not be executed.
     * @param debugLevel The debug level to check against. If the debug level is greater than this, then the runnable will be executed.
     * @param runnable The runnable to execute.
     */
    public static void run(int debugLevel, Runnable runnable) {
        if (DeveloperEnvironment.executeLevel >= debugLevel) {
            runnable.run();
        }
    }

    /**
     * This method will run the given runnable function if and only if the debug level is equal to the given debug level.
     * @param debugLevel The debug level to check against. If the debug level is equal to this, then the runnable will be executed.
     * @param runnable The runnable to execute.
     */
    public static void runOnlyIf(int debugLevel, Runnable runnable) {
        if (DeveloperEnvironment.executeLevel == debugLevel) {
            runnable.run();
        }
    }

}
