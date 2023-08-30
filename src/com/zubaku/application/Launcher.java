package com.zubaku.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/LoginWindow.fxml")));
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/MainWindow.fxml")));

        Scene scene = new Scene(parent, 870, 597);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
