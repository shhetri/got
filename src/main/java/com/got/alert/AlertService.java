package com.got.alert;

import java.util.HashMap;

class AlertService {
    private final HashMap<AlertType, BaseAlert> alertStates = new HashMap<AlertType, BaseAlert>() {
        {
            put(AlertType.SUCCESS, new SuccessAlert());
            put(AlertType.ERROR, new ErrorAlert());
        }
    };

    private BaseAlert currentAlert;

    public AlertService() {
        currentAlert = alertStates.get(AlertType.SUCCESS);
    }

    void alert(String message, String title) {
        currentAlert.alert(message, title);
    }

    void setAlert(AlertType alert) {
        currentAlert = alertStates.get(alert);
    }
}
