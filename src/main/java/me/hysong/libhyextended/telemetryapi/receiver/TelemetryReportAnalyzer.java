package me.hysong.libhyextended.telemetryapi.receiver;

import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;

public class TelemetryReportAnalyzer {
    public static int getVersionOfReport(HttpServletRequest request) {
        try {
            String v = request.getParameter("api");
            return Integer.parseInt(v);
        } catch (Exception e) {
            return -1;
        }
    }
}
