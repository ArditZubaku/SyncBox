package com.zubaku.controllers;

import com.zubaku.models.EmailMessage;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.MessageRendererService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class EmailDetailsController extends BaseController implements Initializable {
  @FXML private Label attachmentsLabel;

  @FXML private HBox downloadsHBox;

  @FXML private Label senderLabel;

  @FXML private Label subjectLabel;

  @FXML private WebView webView;

  public EmailDetailsController(
      EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    EmailMessage emailMessage = emailProcessor.getSelectedEmailMessage();

    subjectLabel.setText(emailMessage.getSubject());
    senderLabel.setText(emailMessage.getSender());

    MessageRendererService service = new MessageRendererService(webView.getEngine());
    service.setEmailMessage(emailMessage);
    service.restart();
  }
}
