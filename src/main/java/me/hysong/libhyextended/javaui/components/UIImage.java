package me.hysong.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Component;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import me.hysong.libhyextended.javaui.components.organizers.UIContainerElement;
import lombok.Getter;

public class UIImage extends JPanel implements UINonContainerElement {

    private Image img;

    @Getter private String name = "";

    @Override
    public UIImage name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIImage && ((UIImage) obj).name.equals(name);
    }

    public UIImage() {
        super();
        name = UUID.randomUUID().toString();
        setOpaque(false);
        setLayout(null);
    }

    public UIImage imageFile(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        ImageIcon imicon = new ImageIcon(image);
        img = imicon.getImage();
        setSize(image.getWidth(), image.getHeight());
        rescale();
        return this;
    }

    public UIImage rescale() {
        img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(img));
        icon.setBackground(getForeground());

        // Change icon size
        icon.setSize(getWidth(), getHeight());
        icon.setBounds(0, 0, getWidth(), getHeight());

        System.out.println("Resized");

        // Add icon to panel
        removeAll();
        add(icon);

        return this;
    }

    @Override
    @Deprecated
    public UIImage text(String text) {return null;}

    @Override
    @Deprecated
    public UIImage font(Font font) {return null;}

    @Override
    @Deprecated
    public UIImage fontSize(int size) {return null;}

    @Override
    @Deprecated
    public UIImage bold(boolean bold) {return null;}

    @Override
    @Deprecated
    public UIImage italic(boolean italic) {return null;}

    @Override
    public Font font() {
        return null;
    }

    @Override
    public UIImage color(Color color) {
        setBackground(color);
        return this;
    }

    @Override
    public UIImage width(int width) {
        setSize(width, getHeight());
        rescale();
        return this;
    }

    @Override
    public UIImage height(int height) {
        setSize(getWidth(), height);
        rescale();
        return this;
    }

    @Override
    public UIImage size(int width, int height) {
        setSize(width, height);
        rescale();
        return this;
    }

    @Override
    public UIImage x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIImage y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIImage location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIImage top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UIImage bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + parentComponent.height() + offset);
        return this;
    }

    @Override
    public UIImage left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UIImage right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + parentComponent.width() + offset, getY());
        return this;
    }

    @Override
    public UIImage centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + parentComponent.width() / 2 - getWidth() / 2 + offset, getY());
        return this;
    }

    @Override
    public UIImage centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + parentComponent.height() / 2 - getHeight() / 2 + offset);
        return this;
    }

    @Override
    public UIImage center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + parentComponent.width() / 2 - getWidth() / 2, ((Component) parentComponent).getY() + parentComponent.height() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIImage action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIImage action(KeyListener onkeyevent) {
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
    @Deprecated
    public String text() {
        return null;
    }

    @Override
    public UIImage opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }
    
    @Override
    public UIImage visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UIImage dilate(float width, float height) {
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
    public UIImage dilate(float width, float height, int x, int y) {
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
    public UIImage dilate(float by) {
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
}
