package me.hy.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.UUID;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.organizers.UIContainerElement;
import lombok.Getter;

public class UIScrollableTextField extends JScrollPane implements UINonContainerElement {

    private JTextArea textArea;
    @Getter private String name = "";

    @Override
    public UIScrollableTextField name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIScrollableTextField && ((UIScrollableTextField) obj).name.equals(name);
    }

    public UIScrollableTextField() {
        super(new JTextArea());
        name = UUID.randomUUID().toString();
        textArea = (JTextArea) getViewport().getView();
    }

    @Override
    public UIScrollableTextField visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UIScrollableTextField color(Color color) {
        setForeground(color);
        return this;
    }

    @Override
    public UIScrollableTextField opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public UIScrollableTextField width(int width) {
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UIScrollableTextField height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UIScrollableTextField size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UIScrollableTextField x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIScrollableTextField y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIScrollableTextField location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIScrollableTextField top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UIScrollableTextField bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UIScrollableTextField left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UIScrollableTextField right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, getY());
        return this;
    }

    @Override
    public UIScrollableTextField centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2 + offset, getY());
        return this;
    }

    @Override
    public UIScrollableTextField centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2 + offset);
        return this;
    }

    @Override
    public UIScrollableTextField center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - getWidth()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - getHeight()) / 2);
        return this;
    }

    @Override
    public UIScrollableTextField action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIScrollableTextField action(KeyListener onkeyevent) {
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
    public UIScrollableTextField text(String text) {
        textArea.setText(text);
        return this;
    }

    @Override
    public UIScrollableTextField font(Font font) {
        setFont(font);
        return this;
    }

    @Override
    public UIScrollableTextField fontSize(int size) {
        setFont(getFont().deriveFont(size));
        return this;
    }

    @Override
    public UIScrollableTextField bold(boolean bold) {
        setFont(getFont().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UIScrollableTextField italic(boolean italic) {
        setFont(getFont().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public Font getFont() {
        return super.getFont();
    }

    @Override
    public String getText() {
        return textArea.getText();
    }

    public boolean isWrap() {
        return textArea.getLineWrap();
    }

    public UIScrollableTextField wrap(boolean wrapText) {
        textArea.setLineWrap(wrapText);
        return this;
    }
}
