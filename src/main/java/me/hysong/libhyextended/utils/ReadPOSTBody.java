package me.hysong.libhyextended.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadPOSTBody {
    public static HashMap<String, String> readFromHttpServletRequest(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("=");
                map.put(split[0], split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
