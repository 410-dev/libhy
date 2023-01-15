package me.hy.libhyextended.javaui.components.organizers;

import java.util.ArrayList;

import me.hy.libhyextended.javaui.components.UIElement;

public interface UIContainerElement extends UIElement {
    public UIContainerElement components(UIElement ...components);
    public ArrayList<UIElement> components();
    public UIElement getComponent(String name);
    public UIContainerElement update();
}
