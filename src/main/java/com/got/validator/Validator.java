package com.got.validator;

import com.got.validator.rules.ValidatorBase;
import javafx.scene.control.Control;

import java.util.*;

public class Validator {

    private Map<Control, List<ValidatorBase>> fieldRules = new LinkedHashMap<>();
    private ValidationType validationType = ValidationType.POPUP;

    public void addRules(Control control, String rules, String messages) {
        List<ValidatorBase> rulesAndMessages = new ArrayList<>();
        String[] ruleSet = rules.split("\\|");
        String[] messageSet = messages.split("\\|");

        for(int i=0; i<ruleSet.length; i++) {
            String message = "";
            if(i>=messageSet.length){
                message = ValidatorFactory.getMessage(ruleSet[i]);
            } else {
                message = messageSet[i];
            }
            rulesAndMessages.add(ValidatorFactory.getValidator(ruleSet[i], control, message, validationType));
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
