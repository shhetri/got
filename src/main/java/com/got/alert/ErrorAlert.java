package com.got.alert;

import javafx.scene.control.Alert;

class ErrorAlert extends BaseAlert {
    @Override
    void alert(String message, String title) {
        showAlert(message, title, Alert.AlertType.ERROR);
    }
}
