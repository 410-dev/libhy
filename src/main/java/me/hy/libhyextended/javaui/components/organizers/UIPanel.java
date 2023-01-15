package me.hy.libhyextended.javaui.components.organizers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.UIElement;
import lombok.Getter;

public class UIPanel extends JPanel implements UIContainerElement {

    private ArrayList<UIElement> elements = new ArrayList<>();

    @Getter private String name = "";

    @Override
    public UIPanel name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIPanel && ((UIPanel) obj).name.equals(name);
    }

    public UIPanel() {
        super();
        name = UUID.randomUUID().toString();
        setOpaque(false);
    }

    public UIPanel opaque(boolean opaque) {
        setOpaque(opaque);
        name = UUID.randomUUID().toString();
        return this;
    }

    @Override
    public UIPanel color(Color color) {
        setOpaque(true);
        setBackground(color);
        return this;
    }

    @Override
    public UIPanel width(int width) {
        size(width, getHeight());
        return this;
    }

    @Override
    public UIPanel height(int height) {
        size(getWidth(), height);
        return this;
    }

    @Override
    public UIPanel size(int width, int height) {
        setSize(width, height);
        update();
        return this;
    }

    @Override
    public UIPanel x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIPanel y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIPanel location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIPanel top(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIPanel bottom(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY() + parentComponent.getHeight());
        return this;
    }

    @Override
    public UIPanel left(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() - getWidth() - offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIPanel right(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIPanel centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2 + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIPanel centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2 + offset, parentComponent.getY() + parentComponent.getHeight() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIPanel center(UIContainerElement parentComponent) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2, parentComponent.getY() + parentComponent.getHeight() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIPanel action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIPanel action(KeyListener onkeyevent) {
        addKeyListener(onkeyevent);
        return this;
    }

    @Override
    public Color getColor() {
        return getBackground();
    }

    @Override
    public UIPanel components(UIElement... components) {
        for (UIElement component : components) {
            elements.add(component);
        }
        return this;
    }

    @Override
    public UIPanel visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UIPanel update() {
        for (UIElement element : elements) {
            if (element instanceof UIContainerElement) {
                ((UIContainerElement) element).size(getWidth(), getHeight());
            }

            add((Component) element);
        }
        return this;
    }

    @Override
    public ArrayList<UIElement> components() {
        return elements;
    }

    @Override
    public UIElement getComponent(String name) {
        for (UIElement element : elements) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }
}
