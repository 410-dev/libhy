package me.hysong.libhyextended.objects;

import com.google.gson.*;
import me.hysong.libhyextended.objects.exception.DataFieldMismatchException;
import me.hysong.libhyextended.utils.ArrayFromJsonArrayConverter;
import me.hysong.libhyextended.utils.ArrayToJsonArrayConverter;
import me.hysong.libhyextended.utils.JsonBeautifier;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class DataObject {

    /**
     * Render the object to JsonObject recursively (If sub-objects are also DataObjects)
     * @param label The label of the object
     * @param typeName The type name of the object
     * @param value The value of the object
     * @param json The JsonObject to render to
     * @return The rendered JsonObject
     */
    private JsonObject toJsonRecursive(String label, String typeName, Object value, JsonObject json) {

        if (typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName()))             json.addProperty(label, Integer.parseInt(value.toString()));
        else if (typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName()))        json.addProperty(label, Float.parseFloat(value.toString()));
        else if (typeName.equals(double.class.getName()) || typeName.equals(Double.class.getName()))      json.addProperty(label, Double.parseDouble(value.toString()));
        else if (typeName.equals(long.class.getName())  || typeName.equals(Long.class.getName()))         json.addProperty(label, Long.parseLong(value.toString()));
        else if (typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName()))    json.addProperty(label, Boolean.parseBoolean(value.toString()));
        else if (typeName.equals(short.class.getName()) || typeName.equals(Short.class.getName()))        json.addProperty(label, Short.parseShort(value.toString()));
        else if (typeName.equals(byte.class.getName()) || typeName.equals(Byte.class.getName()))          json.addProperty(label, Byte.parseByte(value.toString()));
        else if (typeName.equals(char.class.getName()) || typeName.equals(Character.class.getName()))     json.addProperty(label, value.toString().charAt(0));
        else if (typeName.equals(String.class.getName()))                                                 json.addProperty(label, value.toString());
        else if (value.getClass().getSuperclass().getName().equals(Enum.class.getName()))                 json.addProperty(label, value.toString());
        else if (typeName.equals(ArrayList.class.getName()) || typeName.equals(List.class.getName())) {
            JsonArray array = new JsonArray();
            for (Object o : (ArrayList<?>) value) {
                JsonObject object = toJsonRecursive(label, o.getClass().getName(), o, new JsonObject());
                array.add(object.get(label));
            }
            json.add(label, array);
        }
        else if (typeName.equals(HashMap.class.getName())) {
            JsonArray array = new JsonArray();
            for (Object o : ((HashMap<?, ?>) value).keySet()) {
                JsonObject object = toJsonRecursive(o.toString(), ((HashMap<?, ?>) value).get(o).getClass().getName(), ((HashMap<?, ?>) value).get(o), new JsonObject());
                array.add(object);
            }
            json.add(label, array);
        }
        else if (typeName.equals(JsonArray.class.getName()))                                            json.add(label, (JsonArray) value);
        else if (typeName.equals(JsonObject.class.getName()))                                           json.add(label, (JsonObject) value);
        else if (typeName.equals(String[].class.getName()))                                             json.add(label, ArrayToJsonArrayConverter.convert((String[]) value));
        else if (typeName.equals(int[].class.getName()))                                                json.add(label, ArrayToJsonArrayConverter.convert((int[]) value));
        else if (typeName.equals(double[].class.getName()))                                             json.add(label, ArrayToJsonArrayConverter.convert((double[]) value));
        else if (typeName.equals(float[].class.getName()))                                              json.add(label, ArrayToJsonArrayConverter.convert((float[]) value));
        else if (typeName.equals(long[].class.getName()))                                               json.add(label, ArrayToJsonArrayConverter.convert((long[]) value));
        else if (typeName.equals(short[].class.getName()))                                              json.add(label, ArrayToJsonArrayConverter.convert((short[]) value));
        else if (typeName.equals(byte[].class.getName()))                                               json.add(label, ArrayToJsonArrayConverter.convert((byte[]) value));
        else if (typeName.equals(boolean[].class.getName()))                                            json.add(label, ArrayToJsonArrayConverter.convert((boolean[]) value));
        else if (typeName.equals(char[].class.getName()))                                               json.add(label, ArrayToJsonArrayConverter.convert((char[]) value));
        else if (typeName.equals(Object[].class.getName()))                                             json.add(label, ArrayToJsonArrayConverter.convert((Object[]) value));
        else if (typeName.equals(DataObject.class.getName()))                                           json.add(label, ((DataObject) value).toJson());
        else if (value.getClass().getSuperclass().getName().equals(DataObject.class.getName()))         json.add(label, ((DataObject) value).toJson());
        else                                                                                            json.addProperty(label, value.toString());

        return json;
    }

    /**
     * Render the object to JsonObject (Runs toJsonRecursive())
     * @return The rendered JsonObject
     */
    public JsonObject toJson() {
        Class<?> reflectedClass = this.getClass();

        Field[] declaredFields = reflectedClass.getDeclaredFields();

        JsonObject json = new JsonObject();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

                toJsonRecursive(field.getName(), typeName, field.get(this), json);
            }catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to export '" + field.getName() + "' (" + field.getType() + ") to Json.");
            }
        }

        return json;
    }

    /**
     * Render the object to JsonObject, then converts it to a String (Runs toJson())
     * @return The rendered JsonObject with beautified Json
     */
    public String toJsonString() {
        return JsonBeautifier.beautify(toJson());
    }

    /**
     * Render the object to JsonObject, then converts it to a String (Runs toJson())
     * @return The rendered JsonObject with non-beautified Json
     */
    public String toString() {
        return toJson().toString();
    }

    /**
     * Maps the variables from the JsonObject to the object
     * @param o The JsonObject to map from
     * @throws DataFieldMismatchException If the field type or name is not the same as the one in the object
     */
    public void fromJson(JsonObject o) throws DataFieldMismatchException {
        Class<?> reflectedClass = this.getClass();

        Field[] declaredFields = reflectedClass.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {

                // Check if it has name
                String name = field.getName();
                if (!o.has(name)) throw new DataFieldMismatchException(DataFieldMismatchException.FIELD_TYPE_MISMATCH, name, null);

                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

                if (typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())) field.set(this, o.get(name).getAsInt());
                else if (typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName())) field.set(this, o.get(name).getAsFloat());
                else if (typeName.equals(double.class.getName()) || typeName.equals(Double.class.getName())) field.set(this, o.get(name).getAsDouble());
                else if (typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())) field.set(this, o.get(name).getAsLong());
                else if (typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName())) field.set(this, o.get(name).getAsBoolean());
                else if (typeName.equals(short.class.getName()) || typeName.equals(Short.class.getName())) field.set(this, o.get(name).getAsShort());
                else if (typeName.equals(byte.class.getName()) || typeName.equals(Byte.class.getName())) field.set(this, o.get(name).getAsByte());
                else if (typeName.equals(char.class.getName()) || typeName.equals(Character.class.getName())) field.set(this, o.get(name).getAsString());
                else if (type.getSuperclass().getName().equals(DataObject.class.getName())) {
                    JsonObject object = o.get(name).getAsJsonObject();
                    DataObject dataObject = (DataObject) type.getDeclaredConstructor().newInstance();
                    dataObject.fromJson(object);
                    field.set(this, dataObject);
                }
                else if (typeName.equals(String.class.getName())) field.set(this, o.get(name).getAsString());
                else if (typeName.equals(JsonArray.class.getName())) field.set(this, o.get(name).getAsJsonArray());
                else if (typeName.equals(JsonObject.class.getName())) field.set(this, o.get(name).getAsJsonObject());
                else if (type.getSuperclass().getName().equals(Enum.class.getName())) {
                    String enumName = o.get(name).getAsString();
                    Enum<?>[] enumConstants = (Enum<?>[]) type.getEnumConstants();
                    for (Enum<?> enumConstant : enumConstants) {
                        if (enumConstant.name().equals(enumName)) {
                            field.set(this, enumConstant);
                            break;
                        }
                    }

                }
                else if (typeName.startsWith("[L")) {
                    JsonArray jsonArray = o.get(name).getAsJsonArray();
                    field.set(this, ArrayFromJsonArrayConverter.array(jsonArray, type));
                }
                else if (typeName.equals(ArrayList.class.getName())) {
                    JsonArray jsonArray = o.get(name).getAsJsonArray();
                    Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    field.set(this, ArrayFromJsonArrayConverter.arrayList(jsonArray, genericType));
                }
                else System.out.println("Unknown type: " + typeName + " (SuperClass: " + type.getSuperclass().getName() + ", DataObject assignable: "+ type.isAssignableFrom(DataObject.class) + ")");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to import " + field.getName() + " from Json.");
                throw new DataFieldMismatchException(e);
            }
        }
    }

}
