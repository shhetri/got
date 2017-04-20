package com.got.login.strategies;

import com.got.alert.Alerter;
import com.got.database.DB;
import com.got.login.LoggedInUser;
import com.got.login.LoginUser;
import com.got.login.controller.LoginController;
import com.got.window.Window;
import javafx.scene.Node;

public class DatabaseLogin implements Login {

    LoginController loginController;

    public DatabaseLogin(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void login() {
        LoginUser user = DB.table("users")
                .where(builder -> {
                    return builder.where("username", loginController.idField.getText())
                            .orWhere("email", loginController.idField.getText());
                })
                .andWhere("password", loginController.passwordField.getText())
                .getFirst(LoginUser.class);

        if (user != null) {
            LoggedInUser.getInstance().setUser(user);

            if(loginController.roleViews.isEmpty()){
                Window window = Window.WindowBuilder.initialize().withView(loginController.successView).withTitle(loginController.appName).closePreviousWindow((Node) loginController.activeEvent.getSource()).build();
                window.open();

            } else {
                Window window = Window.WindowBuilder.initialize().withView(loginController.roleViews.get(user.getRole())).withTitle(loginController.appName).closePreviousWindow((Node) loginController.activeEvent.getSource()).build();
                window.open();

            }

        } else {
            Alerter.showError("Invalid Login Credentials!");
        }
    }

}
