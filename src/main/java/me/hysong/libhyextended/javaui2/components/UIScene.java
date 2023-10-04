package me.hysong.libhyextended.javaui2.components;

import lombok.Getter;
import lombok.Setter;
import me.hysong.libhyextended.Utils;
import me.hysong.libhyextended.javaui2.data.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class UIScene {

    private ArrayList<Component> components = new ArrayList<>();
    private ArrayList<UIElement> elements = new ArrayList<>();
    private Color color;
    private int width;
    private int height;
    @Getter private Thread asyncUnloadThread;
    @Getter private Thread syncUnloadThread;
    @Getter private Thread asyncLoadThread;
    @Getter private Thread syncLoadThread;

    public UIScene() {
        super();
    }

    public UIScene onLoad(Thread sync, Thread async){
        this.asyncLoadThread = async;
        this.syncLoadThread = sync;
        return this;
    }
    public UIScene onUnload(Thread sync, Thread async){
        this.asyncUnloadThread = async;
        this.syncUnloadThread = sync;
        return this;
    }

    public UIScene onLoadSync(Thread sync) {
        return onLoad(sync, this.asyncLoadThread);
    }

    public UIScene onLoadAsync(Thread async) {
        return onLoad(this.syncLoadThread, async);
    }

    public UIScene onUnloadSync(Thread sync) {
        return onUnload(sync, this.asyncUnloadThread);
    }

    public UIScene onUnloadAsync(Thread async) {
        return onUnload(this.syncUnloadThread, async);
    }

    public UIScene components(UIElement...component) {
        for(UIElement c : component) {
            components.add(c.c);
            elements.add(c);
        }
        return this;
    }

    public UIScene components(Component... component) {
        for (Component c : component) {
            components.add(c);
            elements.add(null);
        }
        return this;
    }

    public ArrayList<Component> components() {
        return components;
    }

    public UIScene color(Color color) {
        this.color = color;
        return this;
    }

    public Color color() {
        return color;
    }


    public UIElement component(String name) {
        for (Object element : components) {
            if (element instanceof UIElement) {
                if (((UIElement) element).name().equals(name)) {
                    return (UIElement) element;
                }
            }
        }
        return null;
    }

    public UIScene width(int width) {
        this.width = width;
        return this;
    }

    public UIScene height(int height) {
        this.height = height;
        return this;
    }

    public int width() {return this.width;}
    public int height() {return this.height;}

    private void centerHorizontal(UIElement element) {
        int middle = Utils.avg(new int[]{0, width});
        int half   = element.width() / 2;
        element.x(middle - half);
    }

    private void centerVertical(UIElement element) {
        int middle = Utils.avg(new int[]{0, height});
        int half   = element.height() / 2;
        element.y(middle - half);
    }

    public JPanel render() {
        JPanel panel = new JPanel();
        panel.setSize(width, height);
        panel.setLayout(null);
        if (width <= 0 || height <= 0) return panel;
        for(int i = 0; i < components.size(); i++) {
            Component o = components.get(i);
            UIElement u = elements.get(i);

            if (o == null) continue;

            if (u != null) {
                int x = u.x();
                int y = u.y();

                for (UIElementPosition position : u.positionQueue()) {
                    switch (position) {
                        case CENTER -> {
                            centerHorizontal(u);
                            centerVertical(u);
                        }
                        case TOP -> u.y(0);
                        case BOTTOM -> u.y(height - u.height());
                        case LEFT -> u.x(0);
                        case RIGHT -> u.x(width - u.width());
                        case RESET -> {
                            u.x(0);
                            u.y(0);
                        }
                    }
                }

                u.positionQueue().clear();

                if (u instanceof UIImage image) {
                    if (image.name().equals("logo")) {
                        System.out.printf("Repositioned from %d,%d to %d,%d%n", u.x(), u.y(), (u.x() + x), (u.y() + y));
                    }
                }
                u.x(u.x() + x);
                u.y(u.y() + y);
                panel.add(u.c);
            }else {
                panel.add(o);
            }
        }
        panel.setBackground(color);
        return panel;
    }

    public UIScene animationStart() {
        Thread mainRunner = new Thread(() -> {
            for (UIElement ue : elements) {
                if (ue == null) continue;
                Animation animation = ue.animation();
                if (animation == null) continue;
                Thread animationThread = new Thread(() -> {
                    for (int i = 0; i < animation.getAnimateFunction().size(); i++) {
                        Thread animationFunction = animation.getAnimateFunction().get(i);
                        Integer loop = animation.getLoops().get(i);
                        boolean infinite = Objects.equals(loop, Animation.LOOP_INFINITE);
                        if (infinite) {
                            while (true) {
                                animationFunction.run();
                            }
                        }else{
                            for(int j = 0; j < loop; j++) {
                                animationFunction.run();
                            }
                        }
                    }
                });
                animationThread.start();
            }
        });
        mainRunner.start();
        return this;
    }

    public void resetComponents() {
        for (UIElement u : elements) {
            u.x(0);
            u.y(0);
        }
    }
}
