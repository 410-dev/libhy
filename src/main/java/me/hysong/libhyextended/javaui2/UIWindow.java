package me.hysong.libhyextended.javaui2;

import javax.swing.*;
import java.awt.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.UUID;

import lombok.Getter;
import me.hysong.libhyextended.javaui2.components.*;
public class UIWindow {
    protected JFrame frame;
    @Getter protected String windowName = UUID.randomUUID().toString();
    protected UIScene currentScene;
    protected ArrayList<UIScene> queuedScenes = new ArrayList<>();
    protected JPanel contentPane;

    public UIWindow(String name) {
        frame = new JFrame();
        contentPane = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);

        contentPane.setLayout(null);
        contentPane.setSize(800, 600);

        frame.setContentPane(contentPane);
        this.windowName = name;
    }

    public UIWindow loadScene(JPanel p) {
        unloadScene();
        p.setSize(frame.getWidth(), frame.getHeight());
        contentPane.add(p);
        return this;
    }

    public UIWindow loadScene(UIScene scene) {
        unloadScene();
        scene.width(frame.getWidth());
        scene.height(frame.getHeight());
        currentScene = scene;
        scene.animationStart();
        if (currentScene != null && currentScene.getAsyncLoadThread() != null) {
            currentScene.getAsyncLoadThread().start();
        }
        if (currentScene != null && currentScene.getSyncLoadThread() != null) {
            currentScene.getSyncLoadThread().run();
        }

        contentPane.add(scene.render());
        contentPane.repaint();
        frame.repaint();
        return this;
    }

    public UIWindow queueScene(UIScene s) {
        queuedScenes.add(s);
        return this;
    }

    private UIWindow loadQueuedScenes() {
        if (queuedScenes.size() > 0) {
            ArrayList<UIScene> localQueue = new ArrayList<>(queuedScenes);
            queuedScenes.clear();
            for(UIScene s : localQueue) {
                loadScene(s);
            }
            return this;
        }else {
            return this;
        }
    }

    public UIScene getCurrentScene() {
        return currentScene;
    }

    public UIWindow unloadScene() {
        if (currentScene != null && currentScene.getAsyncUnloadThread() != null) {
            currentScene.getAsyncUnloadThread().start();
        }
        if (currentScene != null && currentScene.getSyncUnloadThread() != null) {
            currentScene.getSyncUnloadThread().run();
        }
        contentPane.setBackground(null);
        contentPane.removeAll();
        if (currentScene != null) currentScene.resetComponents();
        currentScene = null;
        return this;
    }

    public UIWindow show() {
        frame.setVisible(true);
        return this;
    }

    public UIWindow width(int width) {
        this.frame.setSize(width, this.frame.getHeight());
        this.contentPane.setSize(width, this.frame.getHeight());
        return this;
    }

    public UIWindow height(int height) {
        this.frame.setSize(this.frame.getWidth(), height);
        this.contentPane.setSize(this.frame.getWidth(), height);
        return this;
    }

    public UIWindow title(String text) {
        this.frame.setTitle(text);
        return this;
    }

}
