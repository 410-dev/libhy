package me.hysong.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.util.UUID;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputAdapter;

import me.hysong.libhyextended.javaui.components.organizers.UIContainerElement;
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
        setSize(width, height());
        return this;
    }

    @Override
    public UIScrollableTextField height(int height) {
        setSize(width(), height);
        return this;
    }

    @Override
    public UIScrollableTextField size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UIScrollableTextField x(int x) {
        setLocation(x, y());
        return this;
    }

    @Override
    public UIScrollableTextField y(int y) {
        setLocation(x(), y);
        return this;
    }

    @Override
    public UIScrollableTextField location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIScrollableTextField top(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() - height() - offset);
        return this;
    }

    @Override
    public UIScrollableTextField bottom(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() + ((Component) parentComponent).getHeight() + offset);
        return this;
    }

    @Override
    public UIScrollableTextField left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - width() - offset, y());
        return this;
    }

    @Override
    public UIScrollableTextField right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + ((Component) parentComponent).getWidth() + offset, y());
        return this;
    }

    @Override
    public UIScrollableTextField centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - width()) / 2 + offset, y());
        return this;
    }

    @Override
    public UIScrollableTextField centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(x(), ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - height()) / 2 + offset);
        return this;
    }

    @Override
    public UIScrollableTextField center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + (((Component) parentComponent).getWidth() - width()) / 2, ((Component) parentComponent).getY() + (((Component) parentComponent).getHeight() - height()) / 2);
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
        setFont(font().deriveFont(size));
        return this;
    }

    @Override
    public UIScrollableTextField bold(boolean bold) {
        setFont(font().deriveFont(bold ? Font.BOLD : Font.PLAIN));
        return this;
    }

    @Override
    public UIScrollableTextField italic(boolean italic) {
        setFont(font().deriveFont(italic ? Font.ITALIC : Font.PLAIN));
        return this;
    }

    @Override
    public Font font() {
        return super.getFont();
    }

    @Override
    public String text() {
        return textArea.getText();
    }

    public boolean isWrap() {
        return textArea.getLineWrap();
    }

    public UIScrollableTextField wrap(boolean wrapText) {
        textArea.setLineWrap(wrapText);
        return this;
    }

    @Override
    public UIScrollableTextField dilate(float width, float height) {
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
    public UIScrollableTextField dilate(float width, float height, int x, int y) {
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
    public UIScrollableTextField dilate(float by) {
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
