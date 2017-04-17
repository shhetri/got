package com.got.validator.rules;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.Control;

public abstract class ValidatorBase {

    private String message;
    private String text;
    protected ReadOnlyBooleanWrapper hasErrors = new ReadOnlyBooleanWrapper(false);
    private ValidationType validationType = ValidationType.POPUP;
    private Control control;

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    protected abstract void evaluateRules();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }
}
