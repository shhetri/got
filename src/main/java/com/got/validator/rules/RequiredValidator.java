package com.got.validator.rules;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class RequiredValidator extends ValidatorBase {

    public RequiredValidator(Control control, String message, ValidationType validationType) {
        this.setText(((TextField)control).getText());
        this.setMessage(message);
        this.setControl(control);
        this.setValidationType(validationType);
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
