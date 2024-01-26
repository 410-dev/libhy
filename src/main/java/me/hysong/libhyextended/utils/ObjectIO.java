package me.hysong.libhyextended.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * Serializable object to/from a file
 */
public class ObjectIO {

    /**
     * Write a serializable object to file
     * @param object Object to write
     * @param saveTo File to save to
     * @throws IOException
     */
    public static void write(Object object, File saveTo) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(saveTo); ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(object);
        }
    }

    public static void writeAsJson(Object object, File saveTo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(saveTo, object);
    }

    /**
     * Read a serializable object from file
     * @param readFrom File to read from
     * @return Object read
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object read(File readFrom) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new FileInputStream(readFrom)).readObject();
    }

    public static Object readFromJson(File readFrom, Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(readFrom, clazz);
    }
}
