package com.zubaku.utils;

public enum FXMLFile {
    LoginWindow("LoginWindow.fxml"),
    MainWindow("MainWindow.fxml");

    private final String fxmlFile;

    FXMLFile(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    @Override
    public String toString() {
        return fxmlFile;
    }
}