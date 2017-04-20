package com.got.window;

import com.got.helpers.StringHelper;
import com.got.window.contracts.DataReceivable;
import com.got.window.contracts.WindowInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Window implements WindowInterface {
    private String view;
    private Node component;
    private String title;
    private boolean closePrevious;
    private boolean hidePrevious;
    private boolean isResizeable;
    private boolean showAndWait;
    private Object data;
    private Stage stage;

    private Window(WindowBuilder builder) {
        view = builder.view;
        component = builder.component;
        title = builder.title;
        closePrevious = builder.closePrevious;
        hidePrevious = builder.hidePrevious;
        isResizeable = builder.isResizeable;
        showAndWait = builder.showAndWait;
        data = builder.data;
        stage = builder.stage;
    }

    @Override
    public Stage open() {
        Stage stage = this.stage != null ? this.stage : new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));

            if (data != null) {
                DataReceivable controller = loader.getController();
                controller.receive(data);
            }

            if (hidePrevious) {
                Stage previousStage = (Stage) component.getScene().getWindow();
                previousStage.hide();
            }

            if (closePrevious) {
                Stage previousStage = (Stage) component.getScene().getWindow();
                previousStage.close();
            }

            stage.setResizable(isResizeable);

            if (showAndWait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }

    public static class WindowBuilder {
        private String VIEW_DIRECTORY = StringHelper.stripTrailingSlash(WindowViewResolver.getInstance().getRootView());
        private String view;
        private Node component;
        private String title = "";
        private boolean closePrevious = false;
        private boolean hidePrevious = false;
        private boolean isResizeable = false;
        private boolean showAndWait = false;
        private Object data;
        private Stage stage;

        public static WindowBuilder initialize() {
            return new WindowBuilder();
        }

        public WindowBuilder withView(String view) {
            this.view = String.format("%s/%s.fxml", VIEW_DIRECTORY, StringHelper.stripTrailingSlash(view));

            return this;
        }

        public WindowBuilder isResizeable(boolean isResizeable) {
            this.isResizeable = isResizeable;

            return this;
        }

        public WindowBuilder withTitle(String title) {
            this.title = title;

            return this;
        }

        public WindowBuilder closePreviousWindow(Node component) {
            this.closePrevious = true;
            this.component = component;

            return this;
        }

        public WindowBuilder hidePreviousWindow(Node component) {
            this.hidePrevious = true;
            this.component = component;

            return this;
        }

        public WindowBuilder shouldShowAndWait(boolean showAndWait) {
            this.showAndWait = showAndWait;

            return this;
        }

        public WindowBuilder passData(Object data) {
            this.data = data;

            return this;
        }

        public WindowBuilder setStage(Stage stage) {
            this.stage = stage;

            return this;
        }

        public Window build() {
            return new Window(this);
        }
    }
}
