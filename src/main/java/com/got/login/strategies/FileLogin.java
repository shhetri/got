package com.got.login.strategies;

import com.got.alert.Alerter;
import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.filestorage.contracts.IFile;
import com.got.login.LoggedInUser;
import com.got.login.LoginUser;
import com.got.login.controller.LoginController;
import com.got.window.Window;
import javafx.scene.Node;

public class FileLogin implements Login {

    LoginController loginController;

    public FileLogin(LoginController loginController) {
        this.loginController = loginController;

    }

    @Override
    public void login() {

        Container container = ContainerFactory.getDefaultContainer();
        IFile file = container.make(IFile.class);
        LoginUser user = file.retrieve(loginController.idField.getText(), LoginUser.class);
        if (user == null) {
            Alerter.showError("User does not exists!");
        } else {
            if (user.getUsername().equals(loginController.idField.getText()) && user.getPassword().equals(loginController.idField.getText())) {

                LoggedInUser.getInstance().setUser(user);

                Window window = Window.WindowBuilder.initialize()
                        .withView(loginController.roleViews.isEmpty() ? loginController.successView : loginController.roleViews.get(user.getRole()))
                        .withTitle(loginController.appName)
                        .closePreviousWindow((Node) loginController.activeEvent.getSource())
                        .build();

                window.open();
            } else {
                Alerter.showError("Invalid Login Credentials!");
            }
        }
    }
}
