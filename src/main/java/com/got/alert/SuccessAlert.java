package com.got.alert;

import javafx.scene.control.Alert;

class SuccessAlert extends BaseAlert {
    @Override
    void alert(String message, String title) {
        showAlert(message, title, Alert.AlertType.INFORMATION);
    }
}
