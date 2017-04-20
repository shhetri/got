package com.got.login.strategies;

import com.got.alert.Alerter;
import com.got.database.DB;
import com.got.login.LoggedInUser;
import com.got.login.LoginUser;
import com.got.login.LoginWindow;
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
            Window window = Window.WindowBuilder.initialize()
                    .withView(loginController.roleViews.isEmpty() ? loginController.successView : loginController.roleViews.get(user.getRole()))
                    .withTitle(loginController.appName)
                    .hidePreviousWindow((Node) loginController.activeEvent.getSource())
                    .shouldShowAndWait(true)
                    .build();

            window.open();

            loginController.resetBtn.fire();
            LoginWindow.INSTANCE.getStage().show();

        } else {
            Alerter.showError("Invalid Login Credentials!");
        }
    }

}
