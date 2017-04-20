package com.got.login;

import com.got.container.ContainerFactory;
import com.got.container.contracts.Container;
import com.got.filestorage.contracts.IFile;
import com.got.login.contracts.LoginSystemInterface;
import com.got.window.Window;
import com.got.window.contracts.WindowInterface;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginSystem implements LoginSystemInterface {

    private String view = "login_view";
    private String successView;
    private LoginMethod loginMethod = LoginMethod.DATABASE;
    private String appName = "Login System";
    private HashMap<String, String> roleViews = new HashMap<>();

    public LoginSystem() {
        Container container = ContainerFactory.getDefaultContainer();
        IFile file = container.make(IFile.class);
        LoginUser loginUser = file.retrieve("admin", LoginUser.class);
        if(loginUser == null) {
            loginUser = container.make(LoginUser.class);
            loginUser.setId("1");
            loginUser.setUsername("admin");
            loginUser.setEmail("admin@domain.com");
            loginUser.setPassword("admin");
            loginUser.setRole("admin");
            file.save(loginUser.getUsername(), loginUser);
        }
    }

    public void launch(Stage primaryStage, String appName, String successView) {

        this.appName = appName;

        WindowInterface window = Window.WindowBuilder
                .initialize()
                .withView(view).withTitle(appName)
                .setStage(primaryStage)
                .passData(new ArrayList<Object>(){{add(appName); add(loginMethod); add(successView); add(roleViews);}})
                .build();

        window.open();
    }

    public void launch(Stage primaryStage, String appName, HashMap<String, String> roleViews) {

        this.appName = appName;
        this.roleViews = roleViews;

        WindowInterface window = Window.WindowBuilder
                .initialize()
                .withView(view).withTitle(appName)
                .setStage(primaryStage)
                .passData(new ArrayList<Object>(){{add(appName); add(loginMethod); add(successView); add(roleViews); }})
                .build();

        window.open();
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setLoginMethod(LoginMethod loginMethod) {
        this.loginMethod = loginMethod;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
