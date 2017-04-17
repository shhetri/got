package com.got.validator.rules;

import javafx.scene.control.Control;

public interface AlertStrategy {
    void display(Control control, String message);
    void clear(Control control);
}
