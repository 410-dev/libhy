package me.hy.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.organizers.UIContainerElement;
import lombok.Getter;

public class UIButton extends JButton implements UINonContainerElement {

    @Getter private String name = "";

    @Override
    public UIButton name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIButton && ((UIButton) obj).name.equals(name);
    }

    public UIButton(String text) {
        super(text);
        name = UUID.randomUUID().toString();
    }

    public UIButton() {
        super();
        name = UUID.randomUUID().toString();
        setOpaque(false);
    }

    @Override
    public UIButton text(String text) {
        setText(text);
        return this;
    }

    @Override
    public UIButton font(Font font) {
        setFont(font);
        return this;
    }

    @Override
    public UIButton fontSize(int size) {
        setFont(getFont().deriveFont(size));
        return this;
    }

    @Override
    public UIButton bold(boolean bold) {
        setFont(getFont().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UIButton italic(boolean italic) {
        setFont(getFont().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public Font font() {
        return getFont();
    }

    @Override
    public String text() {
        return getText();
    }

    @Override
    public UIButton color(Color color) {
        setForeground(color);
        return this;
    }

    @Override
    public UIButton width(int width) {
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UIButton height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UIButton size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UIButton x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIButton y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIButton location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIButton top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UIButton bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UIButton left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UIButton right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, getY());
        return this;
    }

    @Override
    public UIButton centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2 + offset, getY());
        return this;
    }

    @Override
    public UIButton centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2 + offset);
        return this;
    }

    @Override
    public UIButton center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2);
        return this;
    }

    @Override
    public UIButton action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIButton action(KeyListener onclickevent) {
        addKeyListener(onclickevent);
        return this;
    }

    @Override
    public UIButton dilate(float width, float height) {
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
    public UIButton dilate(float width, float height, int x, int y) {
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
    public UIButton dilate(float by) {
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
    public Color color() {
        return getForeground();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public UIButton opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public UIButton visible(boolean visible) {
        setVisible(visible);
        return this;
    }
}
