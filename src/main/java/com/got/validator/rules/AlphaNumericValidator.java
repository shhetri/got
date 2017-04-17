package com.got.validator.rules;

import com.got.validator.ValidationType;
import javafx.scene.control.Control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphaNumericValidator extends ValidatorBase {

    public AlphaNumericValidator(Control control, String message, ValidationType validationType) {
        super(control, message, validationType);

    }

    @Override
    protected void evaluateRules() {
        String pattern = "[a-zA-Z0-9\\-_ ]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(this.getText());

        if (matcher.matches()) {
            this.hasErrors.set(false);
        } else {
            this.hasErrors.set(true);
        }
    }
}
