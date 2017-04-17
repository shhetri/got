package com.got.validator.rules;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;

public class PopupStrategy implements AlertStrategy {

    @Override
    public void display(Control control, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

    @Override
    public void clear(Control control) {

    }

}
