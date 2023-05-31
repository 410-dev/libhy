package me.hysong.libhyextended.javaui.components.organizers;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import me.hysong.libhyextended.javaui.components.UIElement;
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
        setLocation(parentComponent.x() + parentComponent.width() + offset, parentComponent.y());
        return this;
    }

    @Override
    public UIPanel bottom(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.x() + parentComponent.width() + offset, parentComponent.y() + parentComponent.height());
        return this;
    }

    @Override
    public UIPanel left(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.x() - getWidth() - offset, parentComponent.y());
        return this;
    }

    @Override
    public UIPanel right(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.x() + parentComponent.width() + offset, parentComponent.y());
        return this;
    }

    @Override
    public UIPanel centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.x() + parentComponent.width() / 2 - getWidth() / 2 + offset, parentComponent.y());
        return this;
    }

    @Override
    public UIPanel centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.x() + parentComponent.width() / 2 - getWidth() / 2 + offset, parentComponent.y() + parentComponent.height() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIPanel center(UIContainerElement parentComponent) {
        setLocation(parentComponent.x() + parentComponent.width() / 2 - getWidth() / 2, parentComponent.y() + parentComponent.height() / 2 - getHeight() / 2);
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
    public Color color() {
        return getBackground();
    }

    @Override
    public String name() {
        return name;
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
            if (element.name().equals(name)) {
                return element;
            }
        }
        return null;
    }


    @Override
    public int x() {
        return getX();
    }

    @Override
    public int y() {
        return getY();
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public int height() {
        return getHeight();
    }

    @Override
    public UIPanel dilate(float width, float height) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.getWidth() * width);
        int newHeight = (int) (this.getHeight() * height);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.getWidth()) / 2;
        int yOffset = (newHeight - this.getHeight()) / 2;

        // Update the size and location of the button to keep the center fixed
        this.size(newWidth, newHeight);
        this.location(this.getX() - xOffset, this.getY() - yOffset);

        return this;
    }

    @Override
    public UIPanel dilate(float width, float height, int x, int y) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.getWidth() * width);
        int newHeight = (int) (this.getHeight() * height);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.getWidth()) / 2;
        int yOffset = (newHeight - this.getHeight()) / 2;

        // Update the size and location of the button to keep the center fixed and shift it
        this.size(newWidth, newHeight);
        this.location(this.getX() - xOffset + x, this.getY() - yOffset + y);

        return this;
    }

    @Override
    public UIPanel dilate(float by) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.getWidth() * by);
        int newHeight = (int) (this.getHeight() * by);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.getWidth()) / 2;
        int yOffset = (newHeight - this.getHeight()) / 2;

        // Update the size and location of the button to keep the center fixed
        this.size(newWidth, newHeight);
        this.location(this.getX() - xOffset, this.getY() - yOffset);

        return this;
    }
}
