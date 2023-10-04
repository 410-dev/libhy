package me.hysong.libhyextended.javaui2.components;

import lombok.Getter;
import me.hysong.libhyextended.javaui2.data.Animation;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public abstract class UIElement {


    private ArrayList<UIElementPosition> positionQueue = new ArrayList<>();
    private String name;
    private Animation animation;
    Component c;

    public UIElement() {
        name(UUID.randomUUID().toString());
//        c.setLayout(null);
//        backgroundColor(Color.RED);
//        foregroundColor(Color.RED);
    }

    public UIElement(String name) {
        name(name);
//        c.setLayout(null);
    }

//    public UIElement(Component c) {
//        name(UUID.randomUUID().toString());
//        c.setBounds(0, 0, width(), height());
//        c.add(c);
////        c.setLayout(null);
//    }
//
//    public UIElement(String name, Component c) {
//        c.setName(name);
//        c.setBounds(0, 0, width(), height());
//        c.add(c);
//        c.setLayout(null);
//    }

    public UIElement name(String name) {
        this.name = name;
        return this;
    }

    // Setters
    public UIElement x(int x) {
        c.setBounds(x, c.getY(), c.getWidth(), c.getHeight());
        return this;
    }
    public UIElement y(int y) {
        c.setBounds(c.getX(), y, c.getWidth(), c.getHeight());
        return this;
    }
    public UIElement width(int width) {
        c.setSize(width, c.getHeight());
        return this;
    }
    public UIElement height(int height) {
        c.setSize(c.getWidth(), height);
        return this;
    }
    public UIElement dilate(float by) {
        return dilate(by, by);
    }
    public UIElement dilate(float width, float height) {
        return dilate(width, height, 0, 0);
    }
    public UIElement dilate(float width, float height, int x, int y) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (c.getWidth() * width);
        int newHeight = (int) (c.getHeight() * height);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - c.getWidth()) / 2;
        int yOffset = (newHeight - c.getHeight()) / 2;

        // Update the size and location of the button to keep the center fixed
        c.setBounds(c.getX() - xOffset + x, c.getY() - yOffset + y, newWidth, newHeight);

        return this;
    }

    public UIElement resize(float width, float height) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (c.getWidth() * width);
        int newHeight = (int) (c.getHeight() * height);

        // Update the size and location of the button to keep the center fixed
        c.setSize(newWidth, newHeight);
        return this;
    }

    public UIElement resize(float by) {
        return resize(by, by);
    }

    public UIElement backgroundColor(Color color) {
        c.setBackground(color);
        return this;
    }

    public UIElement foregroundColor(Color color) {
        c.setForeground(color);
        return this;
    }

    public UIElement visible(boolean visible) {
        c.setVisible(visible);
        return this;
    }

    public UIElement action(MouseInputAdapter onclickevent) {
        c.addMouseListener(onclickevent);
        return this;
    }
    public UIElement action(KeyListener onkeyevent) {
        c.addKeyListener(onkeyevent);
        return this;
    }


    public UIElement addPositionQueue(UIElementPosition... position) {
        this.positionQueue.addAll(Arrays.asList(position));
        return this;
    }

    public UIElement dropLastPositionQueue() {
        this.positionQueue.remove(this.positionQueue.size() - 1);
        return this;
    }

    public UIElement shift(int x, int y) {
        this.x(this.x() + x);
        this.y(this.y() + y);
        return this;
    }

    public UIElement animate(Animation animation) {
        this.animation = animation;
        return this;
    }

//    public UIElement clearShift() {
//
//    }

    // Getters
    public int x() {
        return c.getX();
    }

    public int y() {
        return c.getY();
    }

    public int width() {
        return c.getWidth();
    }

    public int height() {
        return c.getHeight();
    }

    public boolean visible() {
        return c.isVisible();
    }

    public String name() {
        return this.name;
    }

    public Color foregroundColor() {
        return c.getForeground();
    }

    public Color backgroundColor() {
        return c.getBackground();
    }

    public Animation animation() {return this.animation;}

    public ArrayList<UIElementPosition> positionQueue() {
        return this.positionQueue;
    }
}
