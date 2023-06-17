package me.hysong.libhyextended.utils;

import java.io.*;

public class ObjectIO {
    public static void write(Object object, File saveTo) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(saveTo);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(object);
        }
    }

    public static Object read(File readFrom) throws IOException, ClassNotFoundException {
        return new ObjectInputStream(new FileInputStream(readFrom)).readObject();
    }
}
