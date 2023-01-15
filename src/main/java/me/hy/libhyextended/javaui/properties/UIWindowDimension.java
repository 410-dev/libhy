package me.hy.libhyextended.javaui.properties;

import lombok.Getter;

import java.awt.Toolkit;

@Getter
public class UIWindowDimension {
    private int x;
    private int y;
    
    private int width;
    private int height;

    public UIWindowDimension(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public UIWindowDimension() {
        this(0, 0, 800, 600);
    }

    public UIWindowDimension toCenter() {
        this.x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.width / 2);
        this.y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.height / 2);
        return this;
    }

    public UIWindowDimension x(int x) {
        this.x = x;
        return this;
    }

    public UIWindowDimension y(int y) {
        this.y = y;
        return this;
    }

    public UIWindowDimension location(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public UIWindowDimension width(int width) {
        this.width = width;
        return this;
    }

    public UIWindowDimension height(int height) {
        this.height = height;
        return this;
    }

    public UIWindowDimension size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
}

