package com.got.validator;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TextRetriever {

    public static String retrieve(Control control) {

        if (control instanceof TextField) {
            return ((TextField) control).getText();
        } else if (control instanceof ComboBox) {
            return (String) ((ComboBox) control).getSelectionModel().getSelectedItem();
        } else if (control instanceof DatePicker) {
            LocalDate date = ((DatePicker) control).getValue();
            if (date != null) {
                return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            }
        }

        return null;
    }
}
