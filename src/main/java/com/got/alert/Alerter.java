package com.got.alert;

public class Alerter {
    private static AlertService alertService = new AlertService();

    public static void showError(String message) {
        showError(message, null);
    }

    public static void showError(String message, String title) {
        alertService.setAlert(AlertType.ERROR);
        alertService.alert(message, title);
    }

    public static void showSuccess(String message) {
        showSuccess(message, null);
    }

    public static void showSuccess(String message, String title) {
        alertService.setAlert(AlertType.SUCCESS);
        alertService.alert(message, title);
    }
}
