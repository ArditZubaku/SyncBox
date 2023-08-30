module JAVAFX.PROJEKTI {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.zubaku.controller;
    opens com.zubaku.application;
    opens com.zubaku.processors;

}