package com.zubaku.application;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        ViewProcessor viewProcessor = new ViewProcessor(new EmailProcessor());
        viewProcessor.showLoginWindow();
        viewProcessor.updateStyles();
    }
}
