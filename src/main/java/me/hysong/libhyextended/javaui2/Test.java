package me.hysong.libhyextended.javaui2;

import me.hysong.libhyextended.javaui.exceptions.NoSuchUIWindowException;
import me.hysong.libhyextended.javaui2.components.UIButton;
import me.hysong.libhyextended.javaui2.components.UIElementPosition;
import me.hysong.libhyextended.javaui2.components.UIImage;
import me.hysong.libhyextended.javaui2.components.UIScene;
import me.hysong.libhyextended.javaui2.data.Animation;

import java.awt.*;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        UIImage ajax = new UIImage();
        UIImage logo = new UIImage();
        UIScene boot = new UIScene()
            .components(
                logo
                    .width(400)
                    .height(400)
                    .image("/Volumes/Codespace/HermesOS/Desktop/Logo.png")
                    .resize(0.3f)
                    .opaque(false)
                    .backgroundColor(Color.DARK_GRAY)
                    .name("logo")
                    .addPositionQueue(UIElementPosition.CENTER)
                    .visible(true),
                ajax
                    .width(400)
                    .height(400)
                    .image("/Volumes/Codespace/HermesOS/Desktop/ajax.png")
                    .opaque(false)
                    .shift(0, 50)
                    .animate(
                        new Animation()
                            .add(Animation.LOOP_INFINITE, new Thread(() -> {
                                ajax
                                    .dilate(1, 1, 0, 1)
                                    .rotate(30)
                                    .dilate(1, 1, 0, -1);
                                try {Thread.sleep(100);} catch (Exception ignored) {}
                            }))
                    )
                    .name("Ajax")
                    .backgroundColor(Color.GRAY)
                    .resize(0.2f)
                    .addPositionQueue(UIElementPosition.CENTER)
                    .shift(0, 80)
                    .visible(true)
            )
            .color(Color.BLACK)
            .onLoadAsync(
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        UIWindowManager.getWindow("main").loadScene(new UIScene()
                            .components(
                                logo
                                    .addPositionQueue(UIElementPosition.CENTER)
                            )
                            .color(Color.BLACK)
                            .onLoadAsync(
                                new Thread(() -> {
                                    try {Thread.sleep(700);}catch (Exception ignored){}
                                    logo.shift(0, -50);
                                    for(int i = 0; i < 20; i++) {
                                        try {
                                            Thread.sleep(10);
                                        }catch (Exception ignored){}
                                        logo.shift(0, -5);
                                    }
                                    try {
                                        UIWindowManager.getWindow("main").loadScene(
                                            new UIScene()
                                                .components(
                                                    logo
                                                        .addPositionQueue(UIElementPosition.CENTER)
                                                        .shift(0, -250)
                                                )
                                                .color(Color.darkGray)
                                                .onLoadSync(
                                                    new Thread(() -> {
                                                        System.out.println("Loaded login screen");
                                                    })
                                                )
                                        );
                                    } catch (NoSuchUIWindowException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                            )
                        );
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                })
            );


        UIWindowManager.registerWindow(new UIWindow("main")
                .title("Jorg")
                .width(800)
                .height(600)
                .loadScene(boot)
                .show()
        );
    }
}
