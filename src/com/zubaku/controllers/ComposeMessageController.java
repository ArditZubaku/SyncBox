package com.zubaku.controllers;

import com.zubaku.models.EmailAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.EmailSenderService;
import com.zubaku.utils.EmailSendingResult;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ComposeMessageController
    extends BaseController implements Initializable {

  @FXML private Label errorLabel;

  @FXML private HTMLEditor htmlEditor;

  @FXML private TextField recipientTextField;

  @FXML private TextField subjectTextField;

  @FXML private ChoiceBox<EmailAccount> emailAccountChoice;

  public ComposeMessageController(EmailProcessor emailProcessor,
                                  ViewProcessor viewProcessor,
                                  String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @FXML
  void sendButtonAction() {
    EmailSenderService emailSenderService = new EmailSenderService(
        emailAccountChoice.getValue(), subjectTextField.getText(),
        recipientTextField.getText(), htmlEditor.getHtmlText());
    emailSenderService.start();
    emailSenderService.setOnSucceeded(event -> {
      EmailSendingResult result = emailSenderService.getValue();

      switch (result) {
            case SUCCESS -> {
                Stage stage = (Stage) errorLabel.getScene().getWindow();
                viewProcessor.closeStage(stage);
            }
          case FAILED_BY_PROVIDER -> errorLabel.setText("Provider error!");
          case FAILED_BY_UNEXPECTED_ERROR -> errorLabel.setText("Unexpected error!");
        }
    });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    emailAccountChoice.setItems(emailProcessor.getEmailAccounts());
    // Set the first/default email
    emailAccountChoice.setValue(emailProcessor.getEmailAccounts().get(0));
  }
}
