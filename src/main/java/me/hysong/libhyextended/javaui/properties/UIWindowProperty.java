package me.hysong.libhyextended.javaui.properties;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UIWindowProperty {
    private String windowName;
    private String windowTitle;

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

    public String name() {
        return this.windowName;
    }

    public UIWindowProperty title(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }

    public String title() {
        return this.windowTitle;
    }

    public UIWindowProperty size(int width, int height) {
        this.windowDimension.width(width);
        this.windowDimension.height(height);
        return this;
    }

    public int[] size() {
        return new int[] {this.windowDimension.getWidth(), this.windowDimension.getHeight()};
    }

    public UIWindowProperty location(int x, int y) {
        this.windowDimension.x(x);
        this.windowDimension.y(y);
        return this;
    }

    public int[] location() {
        return new int[] {this.windowDimension.getX(), this.windowDimension.getY()};
    }

    public UIWindowProperty width(int windowWidth) {
        this.windowDimension.width(windowWidth);
        return this;
    }

    public int width() {
        return this.windowDimension.getWidth();
    }

    public UIWindowProperty height(int windowHeight) {
        this.windowDimension.height(windowHeight);
        return this;
    }

    public int height() {
        return this.windowDimension.getHeight();
    }

    public UIWindowProperty x(int windowX) {
        this.windowDimension.x(windowX);
        return this;
    }

    public int x() {
        return this.windowDimension.getX();
    }

    public UIWindowProperty y(int windowY) {
        this.windowDimension.y(windowY);
        return this;
    }

    public int y() {
        return this.windowDimension.getY();
    }

    public UIWindowProperty resizable(boolean windowResizable) {
        this.windowResizable = windowResizable;
        return this;
    }

    public boolean resizable() {
        return this.windowResizable;
    }

    public UIWindowProperty visible(boolean windowVisible) {
        this.windowVisible = windowVisible;
        return this;
    }

    public boolean visible() {
        return this.windowVisible;
    }

    public UIWindowProperty dimension(UIWindowDimension windowDimension) {
        this.windowDimension = windowDimension;
        return this;
    }

    public UIWindowDimension dimension() {
        return this.windowDimension;
    }
}
