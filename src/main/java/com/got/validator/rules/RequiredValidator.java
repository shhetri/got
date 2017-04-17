package com.got.validator.rules;

import com.got.validator.ValidationType;
import javafx.scene.control.Control;

public class RequiredValidator extends ValidatorBase {

    public RequiredValidator(Control control, String message, ValidationType validationType) {
        super(control, message, validationType);
    }

    @Override
    protected void evaluateRules() {
        if(this.getText() == null || this.getText().equals("")) {
            this.hasErrors.set(true);
        } else {
            this.hasErrors.set(false);
        }
    }
}
