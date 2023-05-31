package me.hysong.libhyextended.dyn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ExternalJar {

    private final ArrayList<String> classNames = new ArrayList<>();
    private URLClassLoader classLoader = null;

    public ExternalJar from(String filePath) throws IOException {
        JarEntry jar;
        FileInputStream fileInputStream = null;
        JarInputStream jarInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            jarInputStream = new JarInputStream(fileInputStream);
            while (true) {
                jar = jarInputStream.getNextJarEntry();
                if (jar == null) {
                    break;
                }
                //Pick file that has the extension of .class
                if ((jar.getName().endsWith(".class"))) {
                    String className = jar.getName().replaceAll("/", "\\.");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    classNames.add(myClass);
                }
            }

            File file = new File(filePath);
            classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                assert jarInputStream != null;
                fileInputStream.close();
                jarInputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return this;
    }

    public Class<?> require(String requiredClassName) throws ClassNotFoundException {
        Class<?> loadedClass = null;
        for (String className : classNames) {
            loadedClass = classLoader.loadClass(className);
            if (loadedClass.getName().equals(requiredClassName)) {
                break;
            }
        }
        return loadedClass;
    }

    public void close() throws IOException {
        if (classLoader != null) {
            classLoader.close();
        }
    }
}
