package me.hy.libhycore;

/**
 * Logging utility class
 */
public class CoreLogger {

    private static boolean printDebug = false;

    /**
     * Event types for logging
     */
    public static enum EventType {
        /**
         * Informational event
         */
        INFO,

        /**
         * Warning event
         */
        WARNING,

        /**
         * Error event
         */
        ERROR
    }

    /**
     * Prints a message to the console with appropriate formatting according to given event type.
     * The format is: [time] [class] [event type] message
     * @param eventType The event type (EventType.INFO, EventType.WARNING, EventType.ERROR)
     * @param message The message to print
     */
    public static void print(EventType eventType, String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        String callerClass = ste.getClassName();
        String time = CoreDate.timestamp();
        String s = "[" + time + "] [" + callerClass + "] [" + eventType.toString() + "] " + message;
        if (eventType.equals(EventType.ERROR)) {
            System.err.println(s);
        }else{
            System.out.println(s);    
        }
    }

    /**
     * Prints a message to the console with appropriate formatting according to given event type. However, this only prints if debug printing is enabled. It can be enabled by using: CoreLogger.setDebugPrinting(true);
     * The format is: [time] [D] [class] [event type] message
     * @param eventType The event type (EventType.INFO, EventType.WARNING, EventType.ERROR)
     * @param message The message to print
     */
    public static void debug(EventType eventType, String message) {
        if (printDebug) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            String callerClass = ste.getClassName();
            String time = CoreDate.timestamp();
            String s = "[" + time + "] [D] [" + callerClass + "] [" + eventType.toString() + "] " + message;
            if (eventType.equals(EventType.ERROR)) {
                System.err.println(s);
            }else{
                System.out.println(s);
            }
        }
    }

    /**
     * This method enables or disables debug printing.
     * @param printDebug True to enable debug printing, false to disable.
     */
    public static void setDebugPrinting(boolean printDebug) {
        CoreLogger.printDebug = printDebug;
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public CoreLogger() {}
}
