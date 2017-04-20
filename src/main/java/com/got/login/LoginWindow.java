package com.got.login;

import javafx.stage.Stage;

public enum LoginWindow {
    INSTANCE;

    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
