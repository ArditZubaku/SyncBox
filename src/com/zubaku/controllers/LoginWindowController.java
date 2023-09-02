package com.zubaku.controllers;

import com.zubaku.models.EmailAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.LoginService;
import com.zubaku.utils.LoginResult;

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
        if (validFields()) {
            EmailAccount account = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService service = new LoginService(account, emailProcessor);
            service.start();
            service.setOnSucceeded(event -> {
                LoginResult result = service.getValue();
                switch (result) {
                    case SUCCESS -> {
                        viewProcessor.showMainWindow();
                        // Getting the stage of the LoginWindowController in a workaround way:
                        Stage stage = (Stage) emailAddressField.getScene().getWindow();
                        viewProcessor.closeStage(stage);
                        return;
                    }
                    default -> throw new IllegalArgumentException("Unexpected value: " + result);
                }
            });
        }
    }

    private boolean validFields() {
        if (emailAddressField.getText().isEmpty()) {
            errorLabel.setText("Please fill the email");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill the password");
            return false;
        }
        return true;
    }

}