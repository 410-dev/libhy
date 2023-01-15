package me.hy.libhyextended.javaui.components;

import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.organizers.UIContainerElement;
import lombok.Getter;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.UUID;

public class UILabel extends JLabel implements UINonContainerElement {

    @Getter private String name = "";

    @Override
    public UILabel name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UILabel && ((UILabel) obj).name.equals(name);
    }

    public UILabel(String text) {
        super(text);
        name = UUID.randomUUID().toString();
        setOpaque(false);
    }

    public UILabel(){
        super();
        name = UUID.randomUUID().toString();
        setOpaque(false);
    }

    @Override
    public UILabel text(String text) {
        setText(text);
        return this;
    }

    @Override
    public UILabel font(Font font) {
        setFont(font);
        return this;
    }

    @Override
    public UILabel fontSize(int size) {
        setFont(getFont().deriveFont(size));
        return this;
    }

    @Override
    public UILabel bold(boolean bold) {
        setFont(getFont().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UILabel italic(boolean italic) {
        setFont(getFont().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public UILabel color(Color color) {
        setForeground(color);
        return this;
    }

    @Override
    public UILabel width(int width) {
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UILabel height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UILabel size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UILabel x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UILabel y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UILabel location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UILabel top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UILabel bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UILabel left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UILabel right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, getY());
        return this;
    }

    @Override
    public UILabel centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2 + offset, getY());
        return this;
    }

    @Override
    public UILabel centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2 + offset);
        return this;
    }

    @Override
    public UILabel center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2);
        return this;
    }

    @Override
    public UILabel action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UILabel action(KeyListener onclickevent) {
        addKeyListener(onclickevent);
        return this;
    }

    @Override
    public Color getColor() {
        return getForeground();
    }

    @Override
    public UILabel opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public UILabel visible(boolean visible) {
        setVisible(visible);
        return this;
    }
}
