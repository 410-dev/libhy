package me.hysong.libhyextended.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Beautify JSON
 */
public class JsonBeautifier {

    /**
     * Beautify JSON
     * @param jsonObject JSON to beautify
     * @return Beautified JSON
     */
    public static String beautify(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}
