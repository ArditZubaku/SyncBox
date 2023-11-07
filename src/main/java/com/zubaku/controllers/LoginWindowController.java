package com.zubaku.controllers;

import com.zubaku.models.EmailAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.LoginService;
import com.zubaku.utils.Credentials;
import com.zubaku.utils.LoginResult;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController implements Initializable {

  @FXML private TextField emailAddressField;

  @FXML private Label errorLabel;

  @FXML private PasswordField passwordField;

  public LoginWindowController(
      EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    emailAddressField.setText(Credentials.EMAIL_ADDRESS());
    passwordField.setText(Credentials.PASSWORD());
  }

  @FXML
  void loginButtonAction() {
    if (validFields()) {
      EmailAccount account = new EmailAccount(emailAddressField.getText(), passwordField.getText());
      LoginService service = new LoginService(account, emailProcessor);
      // Start will do all the background tasks
      service.start();
      // After finishing the tasks
      service.setOnSucceeded(
          event -> {
            LoginResult result = service.getValue();
            switch (result) {
              case SUCCESS -> {
                if (!viewProcessor.isMainViewInitialized()) viewProcessor.showMainWindow();
                // Getting the stage of the LoginWindowController in a workaround way:
                Stage stage = (Stage) emailAddressField.getScene().getWindow();
                viewProcessor.closeStage(stage);
              }
              case FAILED_BY_CREDENTIALS -> errorLabel.setText("Invalid Credentials");
              case FAILED_BY_NETWORK -> errorLabel.setText("Check Your Network Connection");
              case FAILED_BY_UNEXPECTED_ERROR -> errorLabel.setText("Unexpected Error");
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
