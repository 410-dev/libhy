package me.hysong.libhyextended.dyn;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * A class that loads external JAR files and allows you to access classes inside it.
 */
@Getter
public class ExternalJar {

    private final ArrayList<String> classNames = new ArrayList<>();
    private URLClassLoader classLoader = null;

    /**
     * Loads a JAR file from the specified path.
     *
     * @param filePath The path to the JAR file.
     * @throws IOException If the JAR file cannot be loaded.
     */
    public ExternalJar(String filePath) throws IOException {
        from(filePath);
    }
    public ExternalJar(){}

    /**
     * Loads a JAR file from the specified path.
     *
     * @param filePath The path to the JAR file.
     * @return The ExternalJar object. Use this to method chain the require() method.
     * @throws IOException If the JAR file cannot be loaded.
     */
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

    /**
     * Loads a class from the JAR file.
     *
     * @param requiredClassName The name of the class to load.
     * @return The loaded class.
     * @throws ClassNotFoundException If the class cannot be found.
     */
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

    public ArrayList<String> inspectInherit(String className) {
        ArrayList<String> classes = new ArrayList<>();
        try {
            Class<?> loadedClass = require(className);
            Class<?> superClass = loadedClass.getSuperclass();
            while (superClass != null) {
                classes.add(superClass.getName());
                superClass = superClass.getSuperclass();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            //Ignore
        }
        return classes;
    }

    /**
     * Closes the class loader.
     *
     * @throws IOException If the class loader cannot be closed.
     */
    public void close() throws IOException {
        if (classLoader != null) {
            classLoader.close();
        }
    }
}
