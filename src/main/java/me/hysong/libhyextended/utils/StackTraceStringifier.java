package me.hysong.libhyextended.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceStringifier {
    public static String stringify(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
