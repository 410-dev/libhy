package me.hysong.libhyextended.objects.dataobj2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Setter;
import me.hysong.libhyextended.Utils;
import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.libhyextended.objects.exception.DataFieldMismatchException;
import me.hysong.libhyextended.utils.*;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class DataObject2 implements Serializable {

    private boolean verbose = false;
    @Setter private static boolean verboseByDefault = false;

    /**
     * Creates a new DataObject2
     */
    public DataObject2() {
        verbose = verboseByDefault;
    }

    /**
     * Creates a new DataObject2 with verbose logging
     * @param verbose If verbose logging should be enabled
     */
    public DataObject2(boolean verbose) {
        this.verbose = verbose;
    }


    /**
     * Encode the object to JsonObject recursively (If sub-objects are also DataObject2s)
     * Supported fields:
     * - int / Integer
     * - float / Float
     * - double / Double
     * - long / Long
     * - boolean / Boolean
     * - short / Short
     * - byte / Byte
     * - char / Character
     * - String
     * - Enum
     * - ArrayList / List
     * - HashMap
     * - JsonArray
     * - JsonObject
     * - String[]
     * - int[]
     * - double[]
     * - float[]
     * - long[]
     * - short[]
     * - byte[]
     * - boolean[]
     * - char[]
     * - Object[]: This will just call toString() on the object
     * - DataObject2
     * @param label The label of the object
     * @param typeName The type name of the object
     * @param value The value of the object
     * @param json The JsonObject to encode to
     * @param action The action to run
     * @param nullSafe If the encoder should encode null values. If false, it will throw error.
     * @param nullAlternative If nullSafe is true, then this will be used instead of null
     * @return The encoded JsonObject
     */
    private JsonObject toJsonRecursive(String label, String typeName, Object value, JsonObject json, JSONCodableAction action, boolean nullSafe, String nullAlternative) {

        if (verbose) System.out.println("ENCODING: " + label + " (" + typeName + ")");
        if (verbose) System.out.println(JsonBeautifier.beautify(json));

        // If value is null, then we just set to null
        if (value == null && nullSafe) {
            json.addProperty(label, nullAlternative);
            return json;
        }

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
            for (Object o : (List<?>) value) {
                JsonObject object = toJsonRecursive(label, o.getClass().getName(), o, new JsonObject(), action, nullSafe, nullAlternative);
                array.add(object.get(label));
            }
            json.add(label, array);
        }
        else if (typeName.equals(HashMap.class.getName())) {
            JsonArray array = new JsonArray();
            for (Object o : ((HashMap<?, ?>) value).keySet()) {
                JsonObject object = new JsonObject();
                object.addProperty("key", o == null ? null : o.toString());
                HashMap<?, ?> hashMap = (HashMap<?, ?>) value;
                if (hashMap.get(o).getClass().getSuperclass().getName().equals(DataObject2.class.getName())) {
                    object.add("value", toJsonRecursive(label, hashMap.get(o).getClass().getName(), hashMap.get(o), new JsonObject(), action, nullSafe, nullAlternative));
                }else{
//                    object.addProperty("value", ((HashMap<?, ?>) value).get(o) == null ? null : ((HashMap<?, ?>) value).get(o).toString());
                   // If the value is numeric (float, int, etc) then add it as a number
                    if (hashMap.get(o) instanceof Number) {
                        object.addProperty("value", (Number) hashMap.get(o));
                    }else{
                        object.addProperty("value", hashMap.get(o) == null ? null : hashMap.get(o).toString());
                    }
                }
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
        else if (typeName.equals(DataObject2.class.getName()))                                           json.add(label, toJson(value.getClass(), value, action, nullSafe, nullAlternative));
        else if (value.getClass().getSuperclass().getName().equals(DataObject2.class.getName()))         json.add(label, toJson(value.getClass(), value, action, nullSafe, nullAlternative));
//        else                                                                                            json.addProperty(label, value.toString());
        else {
            if (verbose) System.out.println("Unknown type: " + typeName + ". Trying to find toJson method.");
            // Look through all the superclasses. If it is a DataObject2, then we can encode it.
            boolean isSuperclassDataObject2 = false;
            Class<?> superclass = value.getClass().getSuperclass();
            while (superclass != null) {
                if (superclass.getName().equals(DataObject2.class.getName())) {
                    isSuperclassDataObject2 = true;
                    break;
                }
                superclass = superclass.getSuperclass();
            }

            // If it is a DataObject2, then we can encode it.
            if (isSuperclassDataObject2) {
                json.add(label, ((DataObject2) value).toJson(value.getClass(), value, action, nullSafe, nullAlternative));
            }else{
                // If the value is numeric (float, int, etc) then add it as a number
                if (value instanceof Number) {
                    json.addProperty(label, (Number) value);
                }else{

                    // Look through methods to see if there is a toJson method.
                    Method[] methods = value.getClass().getDeclaredMethods();
                    ArrayList<Method> methodsAvailable = new ArrayList<>();
                    for (Method method : methods) {
                        if (method.getName().equals("toJson")) {
                            methodsAvailable.add(method);
                            break;
                        }
                    }

                    // toJson method not found
                    if (methodsAvailable.isEmpty()) {
                        json.addProperty(label, value.toString());
                    } else {

                        // Expect to have either toJson() or toJson(Class<?> reflectedClass, Object instance, JSONCodableAction action, boolean nullSafe, String nullAlternative)
                        // The first one is higher priority.

                        boolean invokeSuccess = false;
                        for (Method m : methodsAvailable) {
                            try {
                                if (m.getParameterCount() == 0) {
                                    json.add(label, (JsonObject) m.invoke(value));
                                    invokeSuccess = true;
                                    break;
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                invokeSuccess = false;
                            }
                        }
                        if (!invokeSuccess) {
                            for (Method m : methodsAvailable) {
                                try {
                                    if (m.getParameterCount() == 5) {
                                        json.add(label, (JsonObject) m.invoke(value, value.getClass(), value, action, nullSafe, nullAlternative));
                                        invokeSuccess = true;
                                        break;
                                    }
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    invokeSuccess = false;
                                }
                            }
                        }
                        if (!invokeSuccess) {
                            throw new RuntimeException("Failed to export '" + label + "' (" + typeName + ") to Json.");
                        }
                    }
                }
            }
        }

        return json;
    }

    /**
     * Encode the object to JsonObject with default JSONCodableAction.OBJECTIFY action.
     * This method just calls: toJson(JSONCodableAction.OBJECTIFY, nullSafe, nullAlternative)
     * @return The encoded JsonObject
     */
    public JsonObject toJson() {
        return toJson(this.getClass(), this, JSONCodableAction.OBJECTIFY, true, null);
    }

    /**
     * Encode the object to JsonObject with default JSONCodableAction.OBJECTIFY action.
     * @param reflectedClass The class to reflect
     * @param instance The instance of the class
     * @return The encoded JsonObject
     */
    public JsonObject toJson(Class<?> reflectedClass, Object instance) {
        return toJson(reflectedClass, instance, JSONCodableAction.OBJECTIFY, true, null);
    }

    /**
     * Encode the object to JsonObject with default JSONCodableAction.OBJECTIFY action.
     * This method just calls: toJson(JSONCodableAction.OBJECTIFY, nullSafe, nullAlternative)
     * @param nullAlternative This will be used instead of null if found.
     * @return The encoded JsonObject
     */
    public JsonObject toJson(String nullAlternative) {
        return toJson(this.getClass(), this, JSONCodableAction.OBJECTIFY, true, nullAlternative);
    }

    /**
     * Encode the object to JsonObject with default JSONCodableAction.OBJECTIFY action.
     * This method just calls: toJson(JSONCodableAction.OBJECTIFY, nullSafe, nullAlternative)
     * @return The encoded JsonObject
     */
    public JsonObject toJsonNotNullSafe() {
        return toJson(this.getClass(), this, JSONCodableAction.OBJECTIFY, false, null);
    }

    /**
     * Encode the object to JsonObject with specified action.
     * @return The encoded JsonObject
     * @param reflectClass The class to reflect
     * @param instance The instance of the class
     * @param action The action to run
     * @param nullSafe If the encoder should encode null values. If false, it will throw error.
     * @param nullAlternative If nullSafe is true, then this will be used instead of null
     * @throws RuntimeException If the field type is not supported
     */
    public JsonObject toJson(Class<?> reflectClass, Object instance, JSONCodableAction action, boolean nullSafe, String nullAlternative) {

        Field[] declaredFields = reflectClass.getDeclaredFields();

        JsonObject json = new JsonObject();

        boolean isTypeHasCodableAnnotation = hasAnnotationForCodable(action, reflectClass.getAnnotations());

        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (!isTypeHasCodableAnnotation && !hasAnnotationForCodable(action, field.getAnnotations())) {
                if (verbose) System.out.println("Skipping: " + field.getName() + " (" + field.getType().getName() + ") because it has no @JSONCodable annotation.");
                continue;
            }

            // Check if field has @NotJSONCodable annotation
            if (hasAnnotationForNotCodable(field.getAnnotations())) {
                if (verbose) System.out.println("Skipping: " + field.getName() + " (" + field.getType().getName() + ") because it has @NotJSONCodable annotation.");
                continue;
            }

            if (verbose) System.out.println("ENCODING FIELD: " + field.getName() + " (" + field.getType().getName() + ")");

            try {
                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

                toJsonRecursive(field.getName(), typeName, field.get(instance), json, action, nullSafe, nullAlternative);
            }catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to export '" + field.getName() + "' (" + field.getType() + ") to Json.");
            }
        }

        return json;
    }

    /**
     * Checks if the field has the annotation for the specified action
     * @param action The action to check
     * @param annotations The field to check
     * @return If the field has the annotation for the specified action
     */
    private static boolean hasAnnotationForCodable(JSONCodableAction action, Annotation[] annotations) {
        boolean hasAnnotationForExportAction = false;
        for (Annotation annotation : annotations) {
            // Get JSONCodableAction enum, where field is action
            if (annotation instanceof JSONCodable jsonCodable) {
                for (JSONCodableAction localAction : jsonCodable.codableOn()) {
                    if (action == localAction || localAction == JSONCodableAction.ALL) {
                        hasAnnotationForExportAction = true;
                        break;
                    }
                }
            }
        }
        return hasAnnotationForExportAction;
    }

    /**
     * Checks if the field has @Comparable annotation (opposite of @NotComparable)
     * @param annotations The annotations to check
     * @return If the field has the annotation for the specified action
     */
    private static boolean hasAnnotationForComparable(Annotation[] annotations) {
        boolean hasAnnotationForExportAction = false;
        for (Annotation annotation : annotations) {
            // Get JSONCodableAction enum, where field is action
            if (annotation instanceof Comparable) {
                hasAnnotationForExportAction = true;
                break;
            }
        }
        return hasAnnotationForExportAction;
    }

    /**
     * Checks if the field has NotComparable annotation (opposite of @Comparable)
     * @param annotations The annotations to check
     * @return If the field has the annotation for the specified action
     */
    private static boolean hasAnnotationForNotComparable(Annotation[] annotations) {
        boolean hasAnnotationForExportAction = false;
        for (Annotation annotation : annotations) {
            // Get JSONCodableAction enum, where field is action
            if (annotation instanceof NotComparable) {
                hasAnnotationForExportAction = true;
                break;
            }
        }
        return hasAnnotationForExportAction;
    }

    /**
     * Checks if the field has NotJSONCodable annotation (opposite of @JSONCodable)
     * @param annotations The annotations to check
     * @return If the field has the annotation for the specified action
     */
    private static boolean hasAnnotationForNotCodable(Annotation[] annotations) {
        boolean hasAnnotationForExportAction = false;
        for (Annotation annotation : annotations) {
            // Get JSONCodableAction enum, where field is action
            if (annotation instanceof NotJSONCodable) {
                hasAnnotationForExportAction = true;
                break;
            }
        }
        return hasAnnotationForExportAction;
    }


    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, true, null))
     * @return The encoded JsonObject with beautified Json
     */
    public String toJsonString() {
        return JsonBeautifier.beautify(toJson(this.getClass(), this, JSONCodableAction.STRINGIFY, true, null));
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, true, null))
     * @param reflectClass The class to reflect
     * @param instance The instance of the class
     * @return The encoded JsonObject with beautified Json
     */
    public String toJsonString(Class<?> reflectClass, Object instance) {
        return JsonBeautifier.beautify(toJson(reflectClass, instance, JSONCodableAction.STRINGIFY, true, null));
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, true, alternative))
     * @param alternative This will be used instead of null if found.
     * @return The encoded JsonObject with beautified Json
     */
    public String toJsonString(String alternative) {
        return JsonBeautifier.beautify(toJson(this.getClass(), this, JSONCodableAction.STRINGIFY, true, alternative));
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, false, null))
     * @return The encoded JsonObject with beautified Json
     */
    public String toJsonStringNotNullSafe() {
        return JsonBeautifier.beautify(toJsonNotNullSafe());
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY))
     * @return The encoded JsonObject with non-beautified Json
     */
    public String toString() {
        return toJson(this.getClass(), this, JSONCodableAction.STRINGIFY, true, null).toString();
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, true, alternative))
     * @param alternative This will be used instead of null if found.
     * @return The encoded JsonObject with non-beautified Json
     */
    public String toString(String alternative) {
        return toJson(this.getClass(), this, JSONCodableAction.STRINGIFY, true, alternative).toString();
    }

    /**
     * Encode the object to JsonObject, then converts it to a String (Runs toJson(JSONCodableAction.STRINGIFY, false, null))
     * @return The encoded JsonObject with non-beautified Json
     */
    public String toStringNotNullSafe() {
        return toJsonNotNullSafe().toString();
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

            // Get JSONCodableAction enum, where field is action
//            // If action is not ALL or PARSE, skip
//            Annotation[] annotations = field.getAnnotations();
//            if (!isHasAnnotationForCodable(JSONCodableAction.PARSE, field)) continue;

            String fieldName = field.getName();
            String expectedType = field.getType().getName();
            String actualType = null;

            // if field name is serialVersionUID, skip
            if (fieldName.equals("serialVersionUID")) continue;

            try {

                // Check if it has name
                String name = field.getName();
                if (verbose) System.out.print("PARSING FIELD: " + name);
//                if (!o.has(name)) throw new DataFieldMismatchException(DataFieldMismatchException.FIELD_TYPE_MISMATCH, name, null);
                if (!o.has(name)) continue;

                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();
                actualType = typeName;

                if (verbose) System.out.println("(" + typeName + ")");

                if (typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())) field.set(this, o.get(name).getAsInt());
                else if (typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName())) field.set(this, o.get(name).getAsFloat());
                else if (typeName.equals(double.class.getName()) || typeName.equals(Double.class.getName())) field.set(this, o.get(name).getAsDouble());
                else if (typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())) field.set(this, o.get(name).getAsLong());
                else if (typeName.equals(boolean.class.getName()) || typeName.equals(Boolean.class.getName())) field.set(this, o.get(name).getAsBoolean());
                else if (typeName.equals(short.class.getName()) || typeName.equals(Short.class.getName())) field.set(this, o.get(name).getAsShort());
                else if (typeName.equals(byte.class.getName()) || typeName.equals(Byte.class.getName())) field.set(this, o.get(name).getAsByte());
                else if (typeName.equals(char.class.getName()) || typeName.equals(Character.class.getName())) field.set(this, o.get(name).getAsString());
                else if (type.getSuperclass().getName().equals(DataObject2.class.getName())) {
                    JsonObject object = o.get(name).getAsJsonObject();
                    DataObject2 DataObject2 = (DataObject2) type.getDeclaredConstructor().newInstance();
                    DataObject2.fromJson(object);
                    field.set(this, DataObject2);
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

                } else if (typeName.equals(HashMap.class.getName())) {
                    JsonArray jsonObject = o.get(name).getAsJsonArray();
                    Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
                    try {
                        field.set(this, HashMapFromJsonObjectConverter.hashMap(jsonObject, genericType));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Hashmap import failed");
                        throw new DataFieldMismatchException(e, fieldName, expectedType, actualType);
                    }
                }
                    // Dont use the code above!
//                else if (typeName.equals(HashMap.class.getName())) {
//                    JsonArray jsonArray = o.get(name).getAsJsonArray();
//                    HashMap<?, ?> hashMap = new HashMap<>();
//                    for (int i = 0; i < jsonArray.size(); i++) {
//                        JsonObject object = jsonArray.get(i).getAsJsonObject();
//
//                    }
//                    field.set(this, hashMap);
//                }
                else if (typeName.startsWith("[L")) {
                    JsonArray jsonArray = o.get(name).getAsJsonArray();
                    field.set(this, ArrayFromJsonArrayConverter.array(jsonArray, type));
                }
                else if (typeName.equals(ArrayList.class.getName())) {
                    JsonArray jsonArray = o.get(name).getAsJsonArray();
                    Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    field.set(this, ArrayFromJsonArrayConverter.arrayList(jsonArray, genericType));
                }
//                else System.out.println("Unknown type: " + typeName + " (SuperClass: " + type.getSuperclass().getName() + ", DataObject2 assignable: "+ type.isAssignableFrom(DataObject2.class) + ")");
                else {
                    // Look through all the superclasses. If it is a DataObject2, then we can decode it.
                    boolean isSuperclassDataObject2 = false;
                    Class<?> superclass = type.getSuperclass();
                    while (superclass != null) {
                        if (superclass.getName().equals(DataObject2.class.getName())) {
                            isSuperclassDataObject2 = true;
                            break;
                        }
                        superclass = superclass.getSuperclass();
                    }

                    // If it is a DataObject2, then we can decode it.
                    if (isSuperclassDataObject2) {
                        boolean successfullyInstantiated = false;
                        Class<?> instantiatedClass = null;
                        while (successfullyInstantiated) {
                            try {
                                instantiatedClass = Class.forName(typeName);
                                DataObject2 DataObject2 = (DataObject2) instantiatedClass.getDeclaredConstructor().newInstance();
                                DataObject2.fromJson(o.get(name).getAsJsonObject());
                                field.set(this, DataObject2);
                                successfullyInstantiated = true;
                            } catch (ClassNotFoundException e) {
//                                e.printStackTrace();
                                typeName = typeName.substring(0, typeName.length() - 1);
                                System.out.println("Trying to instantiate " + typeName);
                            }
                        }
                    }else{
                        System.err.println("Unknown type: " + typeName + " (SuperClass: " + type.getSuperclass().getName() + ", DataObject2 assignable: "+ type.isAssignableFrom(DataObject2.class) + ")");
                    }
                }

            } catch (DataFieldMismatchException e) {
                throw new DataFieldMismatchException(e, fieldName, expectedType, actualType);
            } catch (JsonProcessingException | InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Maps the variables from the JsonObject to the object, then returns the object immediately.
     * @param o The JsonObject to map from
     * @param clazz The class to map to
     * @param <T> The class to map to
     * @return The mapped object
     * @throws DataFieldMismatchException If the field type or name is not the same as the one in the object
     */
    public <T extends DataObject2> T fromJson(JsonObject o, Class<T> clazz) throws DataFieldMismatchException {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJson(o);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Maps the variables from the JsonString to the object, then returns the object immediately.
     * @param json The JsonString to map from
     * @param clazz The class to map to
     * @param <T> The class to map to
     * @return The mapped object
     * @throws DataFieldMismatchException If the field type or name is not the same as the one in the object
     */
    public <T extends DataObject2> T fromJsonString(String json, Class<T> clazz) throws DataFieldMismatchException {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJsonString(json);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves the object as a Json string to the specified path
     * @param path The path to save to
     * @return If the save was successful
     */
    public boolean save(String path) {
        try {
            StringIO.writeFileToDisk(path, toJsonString());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * Saves the object as a Json string to the specified path under SubsystemEnvironment
     * @param env The SubsystemEnvironment to save to
     * @param path The path in the subsystem environment to save to
     * @return If the save was successful
     */
    public boolean save(SubsystemEnvironment env, String path) {
        try {
            env.writeString(path, toJsonString());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * Loads the object from the specified path
     * @param path The path to load from
     * @param clazz The class which extends DataObject2 to parse to
     * @return The loaded object
     */
    public <T extends DataObject2> T load(String path, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJsonString(StringIO.readFileFromDisk(path));
            this.fromJsonString(obj.toJsonString());
            return obj;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Loads the object from the specified path under SubsystemEnvironment
     * @param env The SubsystemEnvironment to load from
     * @param path The path in the subsystem environment to load from
     * @param clazz The class which extends DataObject2 to parse to
     * @return The loaded object
     */
    public <T extends DataObject2> T load(SubsystemEnvironment env, String path, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.fromJsonString(env.readString(path));
            this.fromJsonString(obj.toJsonString());
            return obj;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if the data fields of the object are the same as the specified object
     * Annotation @Comparable is required for the field to be compared!
     * @param o The object to compare to
     * @return If the data fields of the object are the same as the specified object
     */
    public boolean dataFieldEquals(Object o) {
        return dataFieldEquals(o, false);
    }

    /**
     * Checks if the data fields of the object are the same as the specified object
     * If arrayOrderSensitive is false, the order of the array does not matter.
     * Annotation @Comparable is required for the field to be compared!
     * @param o The object to compare to
     * @param arrayOrderSensitive If the order of the array matters
     * @return If the data fields of the object are the same as the specified object
     */
    public boolean dataFieldEquals(Object o, boolean arrayOrderSensitive) {
        // Compare current fields and the fields of the object
        Class<?> reflectedClass = this.getClass();

        Field[] declaredFields = reflectedClass.getDeclaredFields();
        Field[] declaredFields2 = o.getClass().getDeclaredFields();

        // Check if the fields are the same
        if (declaredFields.length != declaredFields2.length) return false;
        for (Field field : declaredFields) {
            if (!Utils.arrayContains(declaredFields2, field)) return false;
        }

        // Check if type is comparable
        boolean isTypeHasComparableAnnotation = hasAnnotationForComparable(reflectedClass.getAnnotations());

        // Check if the values are the same
        for (Field field : declaredFields) {
            field.setAccessible(true);

            // Check if field has @Comparable annotation
            if (!isTypeHasComparableAnnotation && !hasAnnotationForComparable(field.getAnnotations())) continue;

            // Check if field has @NotComparable annotation
            if (hasAnnotationForNotComparable(field.getAnnotations())) continue;

            try {
                // Get type
                Class<?> type = field.getType();
                String typeName = type.getName();

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