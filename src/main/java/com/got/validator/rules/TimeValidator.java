package com.got.validator.rules;

import com.got.validator.ValidationType;
import javafx.scene.control.Control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidator extends ValidatorBase {
    public TimeValidator(Control control, String message, ValidationType validationType) {
        super(control, message, validationType);
    }

    @Override
    protected void evaluateRules() {
        String pattern = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(this.getText());

        if (matcher.matches()) {
            this.hasErrors.set(false);
        } else {
            this.hasErrors.set(true);
        }
    }
}
