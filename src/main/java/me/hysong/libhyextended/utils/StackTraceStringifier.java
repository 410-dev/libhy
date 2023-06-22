package me.hysong.libhyextended.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Stringify stack trace
 */
public class StackTraceStringifier {

    /**
     * Stringify stack trace
     * @param e Throwable to stringify
     * @return Stringified stack trace
     */
    public static String stringify(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
