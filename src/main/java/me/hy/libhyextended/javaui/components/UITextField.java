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
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UITextField height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UITextField size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UITextField x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UITextField y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UITextField location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UITextField top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UITextField bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UITextField left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UITextField right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, getY());
        return this;
    }

    @Override
    public UITextField centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2 + offset, getY());
        return this;
    }

    @Override
    public UITextField centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2 + offset);
        return this;
    }

    @Override
    public UITextField center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2);
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
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public Color getColor() {
        return getForeground();
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
        setFont(getFont().deriveFont(size));
        return this;
    }

    @Override
    public UITextField bold(boolean bold) {
        setFont(getFont().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UITextField italic(boolean italic) {
        setFont(getFont().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public Font getFont() {
        return super.getFont();
    }

    @Override
    public String getText() {
        return super.getText();
    }
    
}
