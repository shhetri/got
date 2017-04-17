package com.got.validator.contracts;

import javafx.scene.control.Control;

public interface AlertStrategy {
    void display(Control control, String message);
    void clear(Control control);
}
