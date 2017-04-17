package com.got.validator.rules;

import javafx.scene.control.Control;

import java.util.*;

public class Validator {

    private HashMap<Control, List<ValidatorBase>> fieldRules = new LinkedHashMap<>();
    private ValidationType validationType = ValidationType.INLINE;

    public void addRules(Control control, String rules, String messages) {
        List<ValidatorBase> rulesAndMessages = new ArrayList<>();
        String[] ruleSet = rules.split("\\|");
        String[] messageSet = messages.split("\\|");

        for(int i=0; i<ruleSet.length && i<messageSet.length; i++) {
            String message = messageSet[i];
            if(ruleSet[i].equals("alphanumeric")) {
                rulesAndMessages.add(new AlphaNumericValidator(control, message, validationType));
            } else if(ruleSet[i].equals("required")) {
                rulesAndMessages.add(new RequiredValidator(control, message, validationType));
            }
        }
        fieldRules.put(control, rulesAndMessages);
    }

    public void validate() {
        for(Map.Entry<Control, List<ValidatorBase>> entry: fieldRules.entrySet()) {
            for(ValidatorBase validatorBase: entry.getValue()) {
                if(validatorBase.validate()) {
                    return;
                }
            }
        }
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }
}
