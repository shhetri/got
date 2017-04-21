package com.got.validator.rules;

import com.got.validator.ValidationType;
import javafx.scene.control.Control;

public class NumberValidator extends ValidatorBase {
    public NumberValidator(Control control, String message, ValidationType validationType) {
        super(control, message, validationType);
    }

    @Override
    protected void evaluateRules() {
        try {
            Integer.parseInt(this.getText());
            this.hasErrors.set(false);
        } catch (Exception var3) {
            this.hasErrors.set(true);
        }
    }
}
