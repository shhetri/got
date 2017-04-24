package com.got.login.contracts;

import com.got.login.LoginMethod;
import com.got.validator.ValidationType;
import javafx.stage.Stage;

import java.util.Map;

public interface LoginSystemInterface {
    void launch(Stage primaryStage, String appName, String successView);

    void setValidationType(ValidationType validationType);

    void setView(String view);

    void setLoginMethod(LoginMethod loginMethod);

    void launch(Stage primaryStage, String appName, Map<String, String> roleViews);
}
