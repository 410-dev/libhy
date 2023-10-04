package me.hysong.libhyextended.javaui2.components;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIImage extends UIElement {

    private Image currentImg;
    private BufferedImage originalImg;
    @Getter private JPanel panel;

    public UIImage() {
        panel = new JPanel();
        this.c = panel;
        this.c.setLocation(0, 0);
    }

    public UIImage width(int width) {
        panel.setSize(width, this.c.getHeight());
        render();
        return this;
    }

    public UIImage height(int height) {
        panel.setSize(this.c.getWidth(), height);
        render();
        return this;
    }

    public UIImage dilate(float by) {
        super.dilate(by);
        panel.setSize(super.c.getWidth(), super.c.getHeight());
        render();
        return this;
    }

    public UIImage dilate(float width, float height) {
        super.dilate(width, height);
        panel.setSize(super.c.getWidth(), super.c.getHeight());
        render();
        return this;
    }

    public UIImage dilate(float width, float height, int x, int y) {
        super.dilate(width, height, x, y);
        panel.setSize(super.c.getWidth(), super.c.getHeight());
        render();
        return this;
    }

    public UIImage resize(float width, float height) {
        super.resize(width, height);
        panel.setSize(super.c.getWidth(), super.c.getHeight());
        render();
        return this;
    }

    public UIImage resize(float by) {
        return this.resize(by, by);
    }

    public UIImage image(String path) throws IOException {
        originalImg = ImageIO.read(new File(path));
        ImageIcon icon = new ImageIcon(originalImg);
        currentImg = icon.getImage();
        width(originalImg.getWidth());
        height(originalImg.getHeight());
        render();
        return this;
    }

    public UIImage opaque(boolean opaque) {
        panel.setOpaque(opaque);
        return this;
    }

    public boolean opaque() {
        return panel.isOpaque();
    }

//    public UIImage rotate(double degreesInClockwise) {
//        rotation += degreesInClockwise;
//        double radians = Math.toRadians(rotation);
//
//        // Use the originalImg for cumulative rotation
//        BufferedImage rotated = new BufferedImage(originalImg.getWidth(), originalImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = rotated.createGraphics();
//        AffineTransform at = AffineTransform.getRotateInstance(radians, (double) originalImg.getWidth() / 2, (double) originalImg.getHeight() / 2);
//        g2d.setTransform(at);
//        g2d.drawImage(originalImg, 0, 0, null);
//        g2d.dispose();
//
//        // Set the rotated image as the currentImg
//        currentImg = rotated;
//        render();
//
//        return this;
//    }

    public UIImage rotate(int degrees) {
        double angleRadians = Math.toRadians(degrees);
        int width = currentImg.getWidth(null);
        int height = currentImg.getHeight(null);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(angleRadians, (double) width / 2, (double) height / 2);
        g2d.drawImage(currentImg, 0, 0, null);
        g2d.dispose();


        currentImg = rotatedImage;
        render();

        return this;
    }

    public UIImage render() {

        if (currentImg == null) return this;

        currentImg = currentImg.getScaledInstance(width(), height(), Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(currentImg));
        icon.setBackground(foregroundColor());

        // Change icon size
        icon.setSize(width(), height());
        icon.setBounds(0, 0, width(), height());

        // Add icon to panel
        panel.removeAll();
        panel.add(icon);

        return this;
    }
}
