package me.hysong.libhyextended.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.hysong.libhyextended.objects.DataObject;
import me.hysong.libhyextended.objects.dataobj2.DataObject2;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Convert JsonArray to Array
 */
public class ArrayFromJsonArrayConverter {

    private static final Gson gson = new Gson();

    /**
     * Convert JsonArray to Array
     *
     * @param jsonArray JsonArray to convert
     * @param type      Array type
     * @return Array converted from JsonArray
     */
    public static Object array(JsonArray jsonArray, Class<?> type) {
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

    /**
     * Convert JsonArray to ArrayList with type
     *
     * @param jsonArray JsonArray to convert
     * @param type      ArrayList generic type
     * @return ArrayList converted from JsonArray
     * @throws JsonProcessingException
     */
    public static ArrayList<?> arrayList(JsonArray jsonArray, Class<?> type) throws JsonProcessingException {

        if (type.isAssignableFrom(int.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(float.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(double.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(long.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(short.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(byte.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(boolean.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(char.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(String.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(Object.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(DataObject2.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(DataObject.class)) {
            return buildNewArrayList(jsonArray, type);
        } else if (type.isAssignableFrom(ArrayList.class)) {
            return buildNewArrayList(jsonArray, type);
        }

        Type listType = TypeToken.getParameterized(ArrayList.class, type).getType();
        return gson.fromJson(jsonArray, listType);
    }

    private static ArrayList<?> buildNewArrayList(JsonArray jsonArray, Class<?> type) throws JsonProcessingException {
        ArrayList<?> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(gson.fromJson(jsonArray.get(i), (Type) type));
        }
        return list;
    }

}
