package com.got.validator;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class TextRetriever {

    public static String retrieve(Control control) {

        if(control instanceof TextField) {
            return ((TextField) control).getText();
        } else if (control instanceof ComboBox) {
            return (String)((ComboBox) control).getSelectionModel().getSelectedItem();
        }

        return null;
    }

}
