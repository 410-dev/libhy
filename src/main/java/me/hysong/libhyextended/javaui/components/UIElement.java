package me.hysong.libhyextended.javaui.components;

import javax.swing.event.MouseInputAdapter;

import me.hysong.libhyextended.javaui.components.organizers.UIContainerElement;

import java.awt.event.KeyListener;
import java.awt.Color;

public interface UIElement {
    public UIElement name(String name);
    public UIElement visible(boolean visible);
    public UIElement color(Color color);
    public UIElement opaque(boolean opaque);
    public UIElement width(int width);
    public UIElement height(int height);
    public UIElement size(int width, int height);
    public UIElement x(int x);
    public UIElement y(int y);
    public UIElement location(int x, int y);
    public UIElement top(UIContainerElement parentComponent, int offset);
    public UIElement bottom(UIContainerElement parentComponent, int offset);
    public UIElement left(UIContainerElement parentComponent, int offset);
    public UIElement right(UIContainerElement parentComponent, int offset);
    public UIElement centerHorizontal(UIContainerElement parentComponent, int offset);
    public UIElement centerVertical(UIContainerElement parentComponent, int offset);
    public UIElement center(UIContainerElement parentComponent);
    public UIElement action(MouseInputAdapter onclickevent);
    public UIElement action(KeyListener onkeyevent);
    public UIElement dilate(float width, float height);
    public UIElement dilate(float width, float height, int x, int y);
    public UIElement dilate(float by);

    // Getters
    public int x();
    public int y();
    public int width();
    public int height();
    public Color color();
    public String name();
}
