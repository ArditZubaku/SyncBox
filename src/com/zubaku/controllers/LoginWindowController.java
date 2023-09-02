package com.zubaku.controllers;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

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
        System.out.println("Login button pressed.");
        viewProcessor.showMainWindow();

        // Getting the stage of the LoginWindowController in a workaround way:
        Stage stage = (Stage) emailAddressField.getScene().getWindow();
        viewProcessor.closeStage(stage);
    }

}