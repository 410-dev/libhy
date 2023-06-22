package me.hysong.libhyextended.utils;

import com.google.gson.JsonArray;

import java.util.List;

/**
 * Convert Array to JsonArray
 */
public class ArrayToJsonArrayConverter {

    public static JsonArray convert(int[] array) {
        JsonArray jsonArray = new JsonArray();
        for (int i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(double[] array) {
        JsonArray jsonArray = new JsonArray();
        for (double i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(float[] array) {
        JsonArray jsonArray = new JsonArray();
        for (float i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(long[] array) {
        JsonArray jsonArray = new JsonArray();
        for (long i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(short[] array) {
        JsonArray jsonArray = new JsonArray();
        for (short i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(byte[] array) {
        JsonArray jsonArray = new JsonArray();
        for (byte i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(char[] array) {
        JsonArray jsonArray = new JsonArray();
        for (char i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(boolean[] array) {
        JsonArray jsonArray = new JsonArray();
        for (boolean i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(String[] array) {
        JsonArray jsonArray = new JsonArray();
        for (String i : array) {
            jsonArray.add(i);
        }
        return jsonArray;
    }

    public static JsonArray convert(Object[] array) {
        JsonArray jsonArray = new JsonArray();
        for (Object i : array) {
            jsonArray.add(i.toString());
        }
        return jsonArray;
    }

    public static JsonArray convert(List<?> list) {
        JsonArray jsonArray = new JsonArray();
        for (Object i : list) {
            jsonArray.add(i.toString());
        }
        return jsonArray;
    }

}
