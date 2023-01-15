package me.hy.libhyextended.javaui.components.organizers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.UIElement;
import me.hy.libhyextended.javaui.components.UINonContainerElement;
import lombok.Getter;

public class UIStack extends JPanel implements UIContainerElement {

    public static final short VERTICAL = 1;
    public static final short HORIZONTAL = 2;

    private ArrayList<UIElement> componentList = new ArrayList<>();
    private boolean usingCustomSize = false;
    private short type = 0;
    private int offset = 0;

    @Getter private String name = "";

    @Override
    public UIStack name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UIStack && ((UIStack) obj).name.equals(name);
    }

    public UIStack(int width, int height, short type) {
        super();
        setSize(width, height);
        setOpaque(false);
        this.name = UUID.randomUUID().toString();
        this.type = type;
        this.setLayout(null);
    }

    public UIStack(short type) {
        super();
        this.name = UUID.randomUUID().toString();
        setOpaque(false);
        this.type = type;
        this.setLayout(null);
    }

    public UIStack components(UIElement ...components) {
        for(UIElement component : components) {
            this.componentList.add(component);
        }

        return this;
    }

    public UIStack offset(int offset) {
        this.offset = offset;
        update();
        return this;
    }

    public UIStack update() {
        this.removeAll();

        for(int i = 0; i < componentList.size(); i++) {
            
            // Size
            int width = 0;
            int height = 0;
            if (type == UIStack.VERTICAL) {
                width = this.getWidth() - (offset * 2);
                height = this.getHeight() / componentList.size() - (offset * 2);
            }else if (type == UIStack.HORIZONTAL) {
                width = this.getWidth() / componentList.size() - (offset * 2);
                height = this.getHeight() - (offset * 2);
            }else{
                throw new RuntimeException("Invalid type for stack");
            }
            
            // If image, then resize it and center it
            UIElement uicomp = componentList.get(i);

            if (uicomp instanceof UIStack) {
                if (!((UIStack) uicomp).usingCustomSize()) {
                    ((UIStack) uicomp).size(width, height);
                }
            }else if (uicomp instanceof UIContainerElement) {
                ((UIContainerElement) uicomp).size(width, height);
            }else if (uicomp instanceof UINonContainerElement) {
                ((UINonContainerElement) uicomp).size(width, height);
            }

            Component c = (Component) uicomp;
            
            // Set size and location
            c.setSize(width, height);

            if (type == UIStack.VERTICAL) {
                c.setLocation(offset, (i * height) + ((i+1) * offset));
            }else{
                c.setLocation((i * width) + ((i+1) * offset), offset);
            }

            this.add(c);
        }

        return this;
    }

    @Override
    public UIStack color(Color color) {
        setOpaque(true);
        setBackground(color);
        return this;
    }

    @Override
    public UIStack width(int width) {
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UIStack height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UIStack x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIStack y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIStack top(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIStack bottom(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY() + parentComponent.getHeight());
        return this;
    }

    @Override
    public UIStack left(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() - getWidth() - offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIStack right(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIStack centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2 + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIStack centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2 + offset, parentComponent.getY());
        return this;
    }

    @Override
    public UIStack center(UIContainerElement parentComponent) {
        setLocation(parentComponent.getX() + parentComponent.getWidth() / 2 - getWidth() / 2, parentComponent.getY() + parentComponent.getHeight() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIStack action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIStack action(KeyListener onkeyevent) {
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
        return getBackground();
    }

    @Override
    public Font getFont() {
        return super.getFont();
    }

    public UIStack size(int width, int height) {
        usingCustomSize = true;
        setSize(width, height);
        update();
        return this;
    }

    public boolean usingCustomSize() {
        return usingCustomSize;
    }

    public UIStack useParentSize() {
        usingCustomSize = false;
        return this;
    }

    @Override
    public UIStack location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIStack visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UIElement opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public ArrayList<UIElement> components() {
        return componentList;
    }

    @Override
    public UIElement getComponent(String name) {
        for (UIElement element : componentList) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

}
