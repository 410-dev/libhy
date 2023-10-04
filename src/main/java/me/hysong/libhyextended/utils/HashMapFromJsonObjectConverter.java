package me.hysong.libhyextended.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.hysong.libhyextended.objects.DataObject;

import java.util.HashMap;

public class HashMapFromJsonObjectConverter {


    public static HashMap<?, ?> hashMap(JsonArray jsonObject, Class<?> genericType) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (int i = 0; i < jsonObject.size(); i++) {
            JsonObject object = jsonObject.get(i).getAsJsonObject();
            hashMap.put(object.get("key"), object.get("value"));
        }
        return hashMap;
    }
}
