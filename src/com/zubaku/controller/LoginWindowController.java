package com.zubaku.controller;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController extends BaseController{

    @FXML
    private TextField emailAddressField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    public LoginWindowController(EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
        super(emailProcessor, viewProcessor, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        System.out.println("Login button");
    }

}