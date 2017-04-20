package com.got.login.contracts;

import com.got.login.LoginMethod;
import javafx.stage.Stage;

import java.util.HashMap;

public interface LoginSystemInterface {

    void launch(Stage primaryStage, String appName, String successView);
    void setView(String view);
    void setLoginMethod(LoginMethod loginMethod);
    void launch(Stage primaryStage, String appName, HashMap<String, String> roleViews);

}
