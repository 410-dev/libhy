package me.hy.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.UUID;

import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.organizers.UIContainerElement;
import lombok.Getter;

public class UITextField extends JTextField implements UINonContainerElement {

    @Getter private String name = "";
    public UITextField() {
        name = UUID.randomUUID().toString();
    }

    @Override
    public UITextField name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UITextField && ((UITextField) obj).name.equals(name);
    }

    @Override
    public UITextField visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UITextField color(Color color) {
        setForeground(color);
        return this;
    }

    @Override
    public UITextField opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public UITextField width(int width) {
        setSize(width, height());
        return this;
    }

    @Override
    public UITextField height(int height) {
        setSize(width(), height);
        return this;
    }

    @Override
    public UITextField size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UITextField x(int x) {
        setLocation(x, y());
        return this;
    }

    @Override
    public UITextField y(int y) {
        setLocation(x(), y);
        return this;
    }

    @Override
    public UITextField location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UITextField top(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() - height() - offset);
        return this;
    }

    @Override
    public UITextField bottom(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UITextField left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - width() - offset, y());
        return this;
    }

    @Override
    public UITextField right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, y());
        return this;
    }

    @Override
    public UITextField centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - width()) / 2 + offset, y());
        return this;
    }

    @Override
    public UITextField centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - height()) / 2 + offset);
        return this;
    }

    @Override
    public UITextField center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - width()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - height()) / 2);
        return this;
    }

    @Override
    public UITextField action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UITextField action(KeyListener onkeyevent) {
        addKeyListener(onkeyevent);
        return this;
    }

    @Override
    public int x() {
        return super.getX();
    }

    @Override
    public int y() {
        return super.getY();
    }

    @Override
    public int width() {
        return super.getWidth();
    }

    @Override
    public int height() {
        return super.getHeight();
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
    public UITextField text(String text) {
        setText(text);
        return this;
    }

    @Override
    public UITextField font(Font font) {
        setFont(font);
        return this;
    }

    @Override
    public UITextField fontSize(int size) {
        setFont(font().deriveFont(size));
        return this;
    }

    @Override
    public UITextField bold(boolean bold) {
        setFont(font().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UITextField italic(boolean italic) {
        setFont(font().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public Font font() {
        return super.getFont();
    }

    @Override
    public String text() {
        return super.getText();
    }

    @Override
    public UITextField dilate(float width, float height) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.width() * width);
        int newHeight = (int) (this.height() * height);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.width()) / 2;
        int yOffset = (newHeight - this.height()) / 2;

        // Update the size and location of the button to keep the center fixed
        this.size(newWidth, newHeight);
        this.location(this.x() - xOffset, this.y() - yOffset);

        return this;
    }

    @Override
    public UITextField dilate(float width, float height, int x, int y) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.width() * width);
        int newHeight = (int) (this.height() * height);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.width()) / 2;
        int yOffset = (newHeight - this.height()) / 2;

        // Update the size and location of the button to keep the center fixed and shift it
        this.size(newWidth, newHeight);
        this.location(this.x() - xOffset + x, this.y() - yOffset + y);

        return this;
    }

    @Override
    public UITextField dilate(float by) {
        // Calculate the new width and height of the button after dilation
        int newWidth = (int) (this.width() * by);
        int newHeight = (int) (this.height() * by);

        // Calculate the offset to keep the center of the button fixed
        int xOffset = (newWidth - this.width()) / 2;
        int yOffset = (newHeight - this.height()) / 2;

        // Update the size and location of the button to keep the center fixed
        this.size(newWidth, newHeight);
        this.location(this.x() - xOffset, this.y() - yOffset);

        return this;
    }
    
}
