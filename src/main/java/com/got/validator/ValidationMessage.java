package com.got.validator;

import com.got.validator.contracts.AlertStrategy;
import com.got.validator.strategies.InlineStrategy;
import com.got.validator.strategies.PopupStrategy;
import javafx.scene.control.Control;

import java.util.HashMap;

public class ValidationMessage {

    Control control;
    String message;
    HashMap<ValidationType, AlertStrategy> alertStrategies = new HashMap<ValidationType, AlertStrategy>(){
        {
            put(ValidationType.POPUP, new PopupStrategy());
            put(ValidationType.INLINE, new InlineStrategy());
        }
    };
    AlertStrategy activeStrategy;

    public ValidationMessage(Control control, String message, ValidationType validationType) {
        this.control = control;
        this.message = message;
        this.activeStrategy = alertStrategies.get(validationType);
    }

    public void display() {
        activeStrategy.display(control, message);
    }

    public void clear() {
        activeStrategy.clear(control);
    }
}
