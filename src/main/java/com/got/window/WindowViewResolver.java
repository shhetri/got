package com.got.window;

public final class WindowViewResolver {
    private static WindowViewResolver instance = null;
    private String rootView = "";

    private WindowViewResolver() {
    }

    public static WindowViewResolver getInstance() {
        if (instance == null) instance = new WindowViewResolver();

        return instance;
    }

    String getRootView() {
        return rootView;
    }

    public void setRootView(String rootView) {
        this.rootView = rootView;
    }
}
