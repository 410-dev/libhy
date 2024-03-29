package me.hysong.libhyextended.objects;

import com.google.gson.*;
import me.hysong.libhyextended.Utils;
import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.libhyextended.objects.exception.DataFieldMismatchException;
import me.hysong.libhyextended.utils.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Deprecated
public abstract class DataObject implements Serializable {

    private boolean verbose = false;

    public DataObject() {

    }

    public DataObject(boolean verbose) {
        this.verbose = verbose;
    }


    /**
     * Render the object to JsonObject recursively (If sub-objects are also DataObjects)
     * @param label The label of the object
     * @param typeName The type name of the object
     * @param value The value of the object
     * @param json The JsonObject to render to
     * @return The rendered JsonObject
     */
    private JsonObject toJsonRecursive(String label, String typeName, Object value, JsonObject json) {

        if (verbose) System.out.println("RENDERING: " + label + " (" + typeName + ")");
        if (verbose) System.out.println(JsonBeautifier.beautify(json));

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
                JsonObject object = new JsonObject();
                object.addProperty("key", o == null ? null : o.toString());
                object.addProperty("value", ((HashMap<?, ?>) value).get(o) == null ? null : ((HashMap<?, ?>) value).get(o).toString());
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
     * Maps the variables from the JsonString to the object
     * @param json The JsonString to map from
     * @throws DataFieldMismatchException If the field type or name is not the same as the one in the object
     */
    public void fromJsonString(String json) throws DataFieldMismatchException {
        fromJson(JsonParser.parseString(json).getAsJsonObject());
    }

    /**
     * Maps the variables from the JsonObject to the object
     * @param o The JsonObject to map from
     * @throws DataFieldMismatchException If the field type or name is not the same as the one in the object
     */
    public void fromJson(JsonObject o) throws DataFieldMismatchException {
        Class<?> reflectedClass = this.getClass();

        Field[] declaredFields = reflectedClass.getDeclaredFields();

        if (verbose) System.out.println("PARSING: " + reflectedClass.getName());
        if (verbose) System.out.println(JsonBeautifier.beautify(o));

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {

                // Check if it has name
                String name = field.getName();
                if (verbose) System.out.print("PARSING FIELD: " + name);
                if (!o.has(name)) throw new DataFieldMismatchException(DataFieldMismatchException.FIELD_TYPE_MISMATCH, name, null);

                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

                if (verbose) System.out.println("(" + typeName + ")");

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

//                } else if (typeName.equals(HashMap.class.getName())) {
//                    JsonArray jsonObject = o.get(name).getAsJsonArray();
//                    Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
//                    field.set(this, HashMapFromJsonObjectConverter.hashMap(jsonObject, genericType));
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

    public boolean save(String path) {
        try {
            StringIO.writeFileToDisk(path, toJsonString());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean save(SubsystemEnvironment env, String path) {
        try {
            env.writeString(path, toJsonString());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public <T extends DataObject> T load(SubsystemEnvironment env, String path, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJsonString(env.readString(path));
            this.fromJsonString(obj.toJsonString());
            return obj;
        }catch (Exception e) {
            return null;
        }
    }

    public <T extends DataObject> T load(String path, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJsonString(StringIO.readFileFromDisk(path));
            this.fromJsonString(obj.toJsonString());
            return obj;
        }catch (Exception e) {
            return null;
        }
    }

    public boolean dataFieldEquals(Object o) {
        return dataFieldEquals(o, new ArrayList<>(), false);
    }

    public boolean dataFieldEquals(Object o, ArrayList<String> exceptionFieldNames) {
        return dataFieldEquals(o, exceptionFieldNames, false);
    }

    public boolean dataFieldEquals(Object o, boolean arrayOrderSensitive) {
        return dataFieldEquals(o, new ArrayList<>(), arrayOrderSensitive);
    }

    public boolean dataFieldEquals(Object o, ArrayList<String> exceptionFieldNames, boolean arrayOrderSensitive) {
        // Compare current fields and the fields of the object
        Class<?> reflectedClass = this.getClass();

        Field[] declaredFields = reflectedClass.getDeclaredFields();
        Field[] declaredFields2 = o.getClass().getDeclaredFields();

        // Check if the fields are the same
        if (declaredFields.length != declaredFields2.length) return false;
        for (Field field : declaredFields) {
            if (!Utils.arrayContains(declaredFields2, field)) return false;
        }

        // Check if the values are the same
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

                // Check if the field is in the exception list
                if (exceptionFieldNames.contains(field.getName())) continue;

                // Check if the field is an array or list
                if (typeName.startsWith("[L") || typeName.equals(ArrayList.class.getName()) || typeName.equals(List.class.getName())) {
                    if (!arrayOrderSensitive) {
                        Object[] array1 = (Object[]) field.get(this);
                        Object[] array2 = (Object[]) field.get(o);
                        if (!Utils.arrayEquals(array1, array2)) return false;
                    }else {
                        if (!field.get(this).equals(field.get(o))) return false;
                    }
                }else {
                    if (!field.get(this).equals(field.get(o))) return false;
                }
            }catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to compare '" + field.getName() + "' (" + field.getType() + ") to '" + o.getClass().getName() + "'.");
            }
        }

        return true;
    }
}
