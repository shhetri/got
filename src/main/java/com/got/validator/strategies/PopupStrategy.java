package com.got.validator.strategies;

import com.got.alert.Alerter;
import com.got.validator.contracts.AlertStrategy;
import javafx.scene.control.Control;

public class PopupStrategy implements AlertStrategy {

    @Override
    public void display(Control control, String message) {
        Alerter.showError(message);
    }

    @Override
    public void clear(Control control) {

    }

}
