package com.got.login.controller;

import com.got.alert.Alerter;
import com.got.database.DB;
import com.got.database.DBConnector;
import com.got.database.DatabaseType;
import com.got.login.LoginMethod;
import com.got.login.LoginUser;
import com.got.window.Window;
import com.got.window.contracts.DataReceivable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;

public class LoginController implements DataReceivable {

    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label appTitle;

    private String appName;
    private LoginMethod loginMethod;
    private String successView;
    private HashMap<String, String> roleViews;

    @FXML
    void login(ActionEvent event) {
        if (loginMethod.equals(LoginMethod.DATABASE)) {
            System.out.println("Logging in with database...");

            DBConnector.connect(DatabaseType.MYSQL, "localhost", "8889", "movieticketing", "root", "root");
            LoginUser user = DB.table("users")
                    .where(builder -> {
                        return builder.where("username", idField.getText())
                                .orWhere("email", idField.getText());
                    })
                    .andWhere("password", passwordField.getText())
                    .getFirst(LoginUser.class);

            if (user != null) {
                if(roleViews.isEmpty()){
                    Window window = Window.WindowBuilder.initialize().withView(successView).withTitle(appName).closePreviousWindow((Node) event.getSource()).build();
                    window.open();

                } else {
                    Window window = Window.WindowBuilder.initialize().withView(roleViews.get(user.getRole())).withTitle(appName).closePreviousWindow((Node) event.getSource()).build();
                    window.open();

                }

            } else {
                Alerter.showError("Invalid Login Credentials!");
            }

        } else {
            System.out.println("Logging in with file...");

        }
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
        roleViews = (HashMap<String, String>) objects.get(3);
        if (appTitle != null)
            appTitle.setText(appName);
    }
}