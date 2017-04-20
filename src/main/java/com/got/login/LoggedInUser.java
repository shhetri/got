package com.got.login;

public class LoggedInUser {

    private static LoggedInUser instance = null;
    private LoginUser user = null;

    private LoggedInUser() {
    }

    public static LoggedInUser getInstance() {
        if (instance == null) {
            instance = new LoggedInUser();
        }
        return instance;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public void destroyUser() {
        this.user = null;
    }

}
