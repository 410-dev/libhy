package me.hysong.libhyextended.javaui2.components;

import javax.swing.*;

public class UIButton extends UIElement {

    private JButton button;

    public UIButton() {
        button = new JButton();
        this.c = button;
    }

    public UIButton text(String text) {
        button.setText(text);
        return this;
    }

    public String text() {
        return button.getText();
    }
}
