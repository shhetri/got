package com.got.login.controller;

import com.got.container.ContainerFactory;
import com.got.login.LoginMethod;
import com.got.login.strategies.DatabaseLogin;
import com.got.login.strategies.FileLogin;
import com.got.login.strategies.Login;
import com.got.validator.ValidationType;
import com.got.validator.Validator;
import com.got.window.contracts.DataReceivable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController implements DataReceivable {

    @FXML
    public TextField idField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Label appTitle;

    @FXML
    public Button loginBtn;

    @FXML
    public Button resetBtn;

    public String appName;
    public LoginMethod loginMethod;
    public String successView;
    public Map<String, String> roleViews;

    private ValidationType validationType;

    public ActionEvent activeEvent;

    Map<LoginMethod, Login> loginStrategies = new HashMap<LoginMethod, Login>(){{
        put(LoginMethod.DATABASE, new DatabaseLogin(LoginController.this));
        put(LoginMethod.FILE, new FileLogin(LoginController.this));
    }};

    @FXML
    void login(ActionEvent event) {
        activeEvent = event;

        Validator validator = ContainerFactory.getDefaultContainer().make(Validator.class);
        validator.setValidationType(validationType);
        validator.addRules(idField, "required|alphanumeric", "Username/Email is required|Username/Email must be alphanumeric");
        validator.addRules(passwordField, "required", "Password is required");

        if(validator.validate())
            loginStrategies.get(loginMethod).login();

    }

    @FXML
    void reset(ActionEvent event) {
        idField.setText("");
        passwordField.setText("");
    }

    @Override
    public void receive(Object data) {
        List<Object> objects = (List<Object>) data;
        appName = (String) objects.get(0);
        loginMethod = (LoginMethod) objects.get(1);
        successView = (String) objects.get(2);
        roleViews = (Map<String, String>) objects.get(3);
        validationType = (ValidationType) objects.get(4);
        if (appTitle != null)
            appTitle.setText(appName);
    }
}