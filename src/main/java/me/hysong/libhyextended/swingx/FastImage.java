package me.hysong.libhyextended.swingx;

import me.hysong.libhyextended.javaui2.components.UIImage;

import javax.swing.*;
import java.io.IOException;

public class FastImage extends UIImage {

    public FastImage(String imagePath, int width, int height) throws IOException {
        super();
        super.width(width);
        super.height(height);
        super.image(imagePath);
    }

    public FastImage() {}

    public JPanel getImage() {
        return super.render().getPanel();
    }

}
