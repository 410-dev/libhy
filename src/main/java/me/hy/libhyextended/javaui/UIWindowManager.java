package me.hy.libhyextended.javaui;

import java.util.ArrayList;

import me.hy.libhyextended.javaui.exceptions.NoSuchUIWindowException;
import me.hy.libhyextended.javaui.exceptions.RepeatedUIWindowNameException;
import me.hy.libhyextended.javaui.properties.UIWindowProperty;

public class UIWindowManager {
    
    public static final String VERSION = "0.1.0";

    private static ArrayList<UIWindow> windows = new ArrayList<>();

    // Create new window 
    public static UIWindow newWindow(UIWindowProperty property) {

        // Check if there is any window with the same name
        // If there is, throw an exception
        UIWindow w = new UIWindow(property.getWindowName());
        for (UIWindow window : windows) {
            if (window.getName().equals(property.getWindowName())) {
                throw new RepeatedUIWindowNameException(property.getWindowName());
            }
        }

        
        // Set window properties
        w.frame.setLayout(null);
        w = updateProperty(w, property);
        
        // Add window to the list of windows
        windows.add(w);
        return w;
    }

    protected static UIWindow updateProperty(UIWindow w, UIWindowProperty prop) {
        w.frame.setBounds(prop.getWindowDimension().getX(), prop.getWindowDimension().getY(), prop.getWindowDimension().getWidth(), prop.getWindowDimension().getHeight());
        w.frame.setTitle(prop.getWindowTitle());
        w.frame.setResizable(prop.isWindowResizable());
        w.frame.setVisible(prop.isWindowVisible());
        return w;
    }

    public static UIWindow newWindow() throws RepeatedUIWindowNameException {
        return newWindow(new UIWindowProperty());
    }

    public static UIWindow close(String windowName) throws NoSuchUIWindowException {
        for(UIWindow w : windows) {
            if(w.getName().equals(windowName)) {
                windows.remove(w);
                w.frame.dispose();
                return w;
            }
        }

        throw new NoSuchUIWindowException(windowName);
    }

    public static UIWindow close(UIWindow w) throws NoSuchUIWindowException {
        return close(w.getName());
    }

    public static UIWindow getWindow(String windowName) throws NoSuchUIWindowException {
        for(UIWindow w : windows) {
            if(w.getName().equals(windowName)) {
                return w;
            }
        }

        throw new NoSuchUIWindowException(windowName);
    }
}
