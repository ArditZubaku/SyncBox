package com.zubaku.controllers;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

public class ComposeMessageController extends BaseController {

  @FXML private Label errorLabel;

  @FXML private HTMLEditor htmlEditor;

  @FXML private TextField recipientTextField;

  @FXML private TextField subjectTextField;

  public ComposeMessageController(EmailProcessor emailProcessor,
                                  ViewProcessor viewProcessor,
                                  String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @FXML
  void sendButtonAction() {
    System.out.println(
        "Send button action called on ComposeMessageController!");
  }
}
