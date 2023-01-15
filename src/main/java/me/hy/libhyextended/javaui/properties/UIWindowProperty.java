package me.hy.libhyextended.javaui.properties;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UIWindowProperty {
    private String windowName;
    private String windowTitle;

    private int windowWidth;
    private int windowHeight;

    private boolean windowResizable;
    private boolean windowVisible;

    private UIWindowDimension windowDimension;
    
    public UIWindowProperty(String windowName) {
        this.windowName = windowName;
        this.windowTitle = windowName;
        this.windowResizable = true;
        this.windowVisible = true;
        this.windowDimension = new UIWindowDimension(0, 0, 800, 600);
    }

    public UIWindowProperty() {
        this(UUID.randomUUID().toString());
    }

    public UIWindowProperty name(String windowName) {
        this.windowName = windowName;
        return this;
    }

    public UIWindowProperty title(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }

    public UIWindowProperty width(int windowWidth) {
        this.windowDimension.width(windowWidth);
        return this;
    }

    public UIWindowProperty height(int windowHeight) {
        this.windowDimension.height(windowHeight);
        return this;
    }

    public UIWindowProperty x(int windowX) {
        this.windowDimension.x(windowX);
        return this;
    }

    public UIWindowProperty y(int windowY) {
        this.windowDimension.y(windowY);
        return this;
    }

    public UIWindowProperty resizable(boolean windowResizable) {
        this.windowResizable = windowResizable;
        return this;
    }

    public UIWindowProperty visible(boolean windowVisible) {
        this.windowVisible = windowVisible;
        return this;
    }

    public UIWindowProperty dimension(UIWindowDimension windowDimension) {
        this.windowDimension = windowDimension;
        return this;
    }
}
