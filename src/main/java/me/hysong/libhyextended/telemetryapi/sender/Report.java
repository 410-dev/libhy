package me.hysong.libhyextended.telemetryapi.sender;

import com.google.gson.JsonObject;
import me.hysong.libhycore.CoreBase64;
import me.hysong.libhycore.CoreDate;
import me.hysong.libhyextended.request.Request;
import me.hysong.libhyextended.request.RequestParameter;
import me.hysong.libhyextended.telemetryapi.receiver.TelemetryAPIStateCodes;

import java.io.IOException;
import java.util.HashMap;

public class Report {

    public static String getCallerClassName(int depth) {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        return stElements[depth].getClassName();
    }

    public static String getCallerMethodName(int depth) {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        return stElements[depth].getMethodName();
    }

    public static boolean report(boolean silentOnStackTrace, String api, String namespace, String uniqueness, String message) {
        try {
            JsonObject parameters = new JsonObject();
            parameters.addProperty("apiver", "1");
            parameters.addProperty("silent", silentOnStackTrace);
            parameters.addProperty("namespace", namespace);
            parameters.addProperty("uniqueness", uniqueness);
            parameters.addProperty("timestamp", CoreDate.timestamp());
            parameters.addProperty("epoch", CoreDate.mSecSince1970());

            String returnCode = Request.request(api, "POST", new HashMap<>(){
                {
                    put("Content-Type", "text/plain");
                    put("Accept", "text/plain");
                }
            }, message, new RequestParameter[]{
                new RequestParameter("api", "1"),
                new RequestParameter("params", CoreBase64.encode(parameters.toString()))
            });
            returnCode = returnCode.trim();
            return returnCode.equals(TelemetryAPIStateCodes.OK);
        } catch (IOException e) {
            if (!silentOnStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
