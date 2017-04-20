package com.got.validator;

import com.got.validator.rules.AlphaNumericValidator;
import com.got.validator.rules.RequiredValidator;
import com.got.validator.rules.ValidatorBase;
import javafx.scene.control.Control;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {

    static Map<String, Class> validators = new HashMap<String, Class>() {
        {
            put("alphanumeric", AlphaNumericValidator.class);
            put("required", RequiredValidator.class);
        }
    };

    static Map<String, String> messages = new HashMap<String, String>() {
        {
            put("alphanumeric", "This field must be alphanumeric!");
            put("required", "This field must be alphanumeric!");
        }
    };

    public void addValidator(Class clazz, String name, String message) {
        validators.put(name, clazz);
        messages.put(name, message);
    }

    public static ValidatorBase getValidator(String rule, Control control, String message, ValidationType validationType) {
        try {
            return (ValidatorBase) validators.get(rule).getConstructor(Control.class, String.class, ValidationType.class).newInstance(control, message, validationType);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMessage(String rule) {
        return messages.get(rule);
    }
}
