package me.hysong.libhyextended.utils;
import lombok.Getter;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;

@Getter
public class ExternalObject implements Serializable {

    private final String name;
    private final File serviceFile;

    public ExternalObject(File serviceFile, String className) {
        this.serviceFile = serviceFile;
        this.name = className;
    }

    public Class<?> loadClass() {
        try {
            String jarFilePath = serviceFile.getAbsolutePath();
            URL jarUrl = new URL("file://" + jarFilePath);
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
            Class<?> finalLoadedClass = classLoader.loadClass(name);
            classLoader.close();
            return finalLoadedClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
