package me.hy.libhyextended.javaui;

import me.hy.libhyextended.javaui.components.UIButton;
import me.hy.libhyextended.javaui.components.UIImage;
import me.hy.libhyextended.javaui.components.UIScene;
import me.hy.libhyextended.javaui.components.organizers.VerticalStack;
import me.hy.libhyextended.javaui.properties.UIWindowProperty;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class UITest {
    public static void main(String[] args) throws Exception {
        UIWindow window = UIWindowManager.newWindow(
                new UIWindowProperty()
                        .title("Test Window")
                        .size(800, 600)
        );

        UIScene scene = new UIScene();
        VerticalStack vstack = new VerticalStack();
        UIButton button = new UIButton("Test Button");
        UIImage image = new UIImage();

        scene.components(
            vstack
                .components(
                    button
                        .color(Color.BLACK)
                        .size(100, 50)
                        .action(new MouseInputAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.exit(0);
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                button.dilate(1f/1.03f);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                button.dilate(1.03f);
                            }
                        }),
                    image
                        .imageFile("/Volumes/Codespace/asset-test/btn.png")
                        .action(
                            new MouseInputAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    System.exit(0);
                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    image.dilate(1f/1.03f);
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    image.dilate(1.03f);
                                }
                            }
                        )
            ).offset(20)
        );



        window.loadScene(scene);
    }
}
