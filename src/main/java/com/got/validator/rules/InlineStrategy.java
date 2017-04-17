package com.got.validator.rules;

import javafx.scene.control.Control;
import javafx.scene.control.Label;

public class InlineStrategy implements AlertStrategy {

    @Override
    public void display(Control control, String message) {

        Label label = (Label)control.getScene().lookup("#"+control.getId()+"Label");
        label.setText(message);

    }

    @Override
    public void clear(Control control) {

        Label label = (Label)control.getScene().lookup("#"+control.getId()+"Label");
        label.setText("");

    }

}
