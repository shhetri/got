package com.got.validator.rules;

import com.got.validator.TextRetriever;
import com.got.validator.ValidationMessage;
import com.got.validator.ValidationType;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.Control;

public abstract class ValidatorBase {

    private String message;
    private String text;
    protected ReadOnlyBooleanWrapper hasErrors = new ReadOnlyBooleanWrapper(false);
    private ValidationType validationType = ValidationType.POPUP;
    private Control control;

    public ValidatorBase(Control control, String message, ValidationType validationType) {
        this.message = message;
        this.validationType = validationType;
        this.control = control;
        this.text = TextRetriever.retrieve(control);
    }

    // Template Method Design Pattern
    public final boolean validate() {
        this.evaluateRules();
        this.onRulesEvaluation();

        return this.hasErrors.get();
    }

    private void onRulesEvaluation() {
        // Strategy Design Pattern
        ValidationMessage validationMessage = new ValidationMessage(control, message, validationType);
        if(this.hasErrors.get()) {
            validationMessage.display();
        } else {
            validationMessage.clear();
        }
    }

    protected abstract void evaluateRules();

    public String getText() {
        return text;
    }
}
