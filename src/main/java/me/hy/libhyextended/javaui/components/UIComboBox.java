package me.hy.libhyextended.javaui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.ItemListener;

import java.util.UUID;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.MouseInputAdapter;

import me.hy.libhyextended.javaui.components.organizers.UIContainerElement;

public class UIComboBox<T> extends JComboBox<T> implements UINonContainerElement {

    private String name = "";

    public UIComboBox() {
        super();
        name = UUID.randomUUID().toString();
    }
    
    public UIComboBox(ComboBoxModel<T> aModel) {
        super(aModel);
        name = UUID.randomUUID().toString();
    }

    public UIComboBox(T[] items) {
        super(items);
        name = UUID.randomUUID().toString();
    }

    public UIComboBox(Vector<T> items) {
        super(items);
        name = UUID.randomUUID().toString();
    }

    public UIComboBox<T> add(T item) {
        addItem(item);
        return this;
    }

    public UIComboBox<T> add(T[] items) {
        for (T item : items) {
            addItem(item);
        }
        return this;
    }

    public UIComboBox<T> add(Vector<T> items) {
        for (T item : items) {
            addItem(item);
        }
        return this;
    }

    public UIComboBox<T> remove(T item) {
        removeItem(item);
        return this;
    }

    public UIComboBox<T> remove(T[] items) {
        for (T item : items) {
            removeItem(item);
        }
        return this;
    }

    public UIComboBox<T> remove(Vector<T> items) {
        for (T item : items) {
            removeItem(item);
        }
        return this;
    }

    public UIComboBox<T> clear() {
        removeAllItems();
        return this;
    }

    public UIComboBox<T> selected(T item) {
        setSelectedItem(item);
        return this;
    }

    public UIComboBox<T> selected(int index) {
        setSelectedIndex(index);
        return this;
    }

    public UIComboBox<T> itemAction(ItemListener itemListener) {
        addItemListener(itemListener);
        return this;
    }

    public Object selectedObject() {
        return super.getSelectedItem();
    }

    public int selectedIndex() {
        return super.getSelectedIndex();
    }

    public Object[] selectedObjects() {
        return super.getSelectedObjects();
    }

    public UIComboBox<T> insert(int index, T item) {
        insertItemAt(item, index);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UIComboBox<T> name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UIComboBox<T> visible(boolean visible) {
        setVisible(visible);
        return this;
    }

    @Override
    public UIComboBox<T> color(Color color) {
        setBackground(color);
        return this;
    }

    @Override
    public UIComboBox<T> opaque(boolean opaque) {
        setOpaque(opaque);
        return this;
    }

    @Override
    public UIComboBox<T> width(int width) {
        setSize(width, getHeight());
        return this;
    }

    @Override
    public UIComboBox<T> height(int height) {
        setSize(getWidth(), height);
        return this;
    }

    @Override
    public UIComboBox<T> size(int width, int height) {
        setSize(width, height);
        return this;
    }

    @Override
    public UIComboBox<T> x(int x) {
        setLocation(x, getY());
        return this;
    }

    @Override
    public UIComboBox<T> y(int y) {
        setLocation(getX(), y);
        return this;
    }

    @Override
    public UIComboBox<T> location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    @Override
    public UIComboBox<T> top(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() - getHeight() - offset);
        return this;
    }

    @Override
    public UIComboBox<T> bottom(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + parentComponent.getHeight() + offset);
        return this;
    }

    @Override
    public UIComboBox<T> left(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() - getWidth() - offset, getY());
        return this;
    }

    @Override
    public UIComboBox<T> right(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + parentComponent.getWidth() + offset, getY());
        return this;
    }

    @Override
    public UIComboBox<T> centerHorizontal(UIContainerElement parentComponent, int offset) {
        setLocation(((Component) parentComponent).getX() + parentComponent.getWidth() / 2 - getWidth() / 2 + offset, getY());
        return this;
    }

    @Override
    public UIComboBox<T> centerVertical(UIContainerElement parentComponent, int offset) {
        setLocation(getX(), ((Component) parentComponent).getY() + parentComponent.getHeight() / 2 - getHeight() / 2 + offset);
        return this;
    }

    @Override
    public UIComboBox<T> center(UIContainerElement parentComponent) {
        setLocation(((Component) parentComponent).getX() + parentComponent.getWidth() / 2 - getWidth() / 2, ((Component) parentComponent).getY() + parentComponent.getHeight() / 2 - getHeight() / 2);
        return this;
    }

    @Override
    public UIComboBox<T> action(MouseInputAdapter onclickevent) {
        addMouseListener(onclickevent);
        return this;
    }

    @Override
    public UIComboBox<T> action(KeyListener onkeyevent) {
        addKeyListener(onkeyevent);
        return this;
    }

    @Override
    public Color getColor() {
        return getBackground();
    }

    @Deprecated
    @Override
    public UIComboBox<T> text(String text) {
        return null;
    }

    @Override
    public UIComboBox<T> font(Font font) {
        setFont(font);
        return this;
    }

    @Override
    public UIComboBox<T> fontSize(int size) {
        setFont(getFont().deriveFont(size));
        return this;
    }

    @Override
    public UIComboBox<T> bold(boolean bold) {
        setFont(getFont().deriveFont(bold ? getFont().getStyle() | Font.BOLD : getFont().getStyle() & ~Font.BOLD));
        return this;
    }

    @Override
    public UIComboBox<T> italic(boolean italic) {
        setFont(getFont().deriveFont(italic ? getFont().getStyle() | Font.ITALIC : getFont().getStyle() & ~Font.ITALIC));
        return this;
    }

    @Deprecated
    @Override
    public String getText() {
        return name;
    }
    
}
