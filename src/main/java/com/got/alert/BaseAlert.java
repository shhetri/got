package com.got.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

abstract class BaseAlert {
    void showAlert(String message, String title, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    abstract void alert(String message, String title);
}
