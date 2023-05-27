package me.hy.libhyextended.utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.lang.reflect.Array;

public class ArrayFromJsonArrayConverter {

    private static final Gson gson = new Gson();

    public static Object convert(JsonArray jsonArray, Class<?> type) {
        if (type.isAssignableFrom(int[].class)) {
            return gson.fromJson(jsonArray, int[].class);
        } else if (type.isAssignableFrom(float[].class)) {
            return gson.fromJson(jsonArray, float[].class);
        } else if (type.isAssignableFrom(double[].class)) {
            return gson.fromJson(jsonArray, double[].class);
        } else if (type.isAssignableFrom(long[].class)) {
            return gson.fromJson(jsonArray, long[].class);
        } else if (type.isAssignableFrom(short[].class)) {
            return gson.fromJson(jsonArray, short[].class);
        } else if (type.isAssignableFrom(byte[].class)) {
            return gson.fromJson(jsonArray, byte[].class);
        } else if (type.isAssignableFrom(boolean[].class)) {
            return gson.fromJson(jsonArray, boolean[].class);
        } else if (type.isAssignableFrom(char[].class)) {
            return gson.fromJson(jsonArray, char[].class);
        } else if (type.isAssignableFrom(String[].class)) {
            return gson.fromJson(jsonArray, String[].class);
        } else if (type.isAssignableFrom(Object[].class)) {
            Class<?> componentType = type.getComponentType();
            int length = jsonArray.size();
            Object array = Array.newInstance(componentType, length);

            for (int i = 0; i < length; i++) {
                Array.set(array, i, gson.fromJson(jsonArray.get(i), componentType));
            }

            return array;
        } else {
            throw new IllegalArgumentException("Unsupported array type: " + type.getName());
        }
    }
}
