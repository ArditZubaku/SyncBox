package com.zubaku.controllers;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class EmailDetailsController extends BaseController {
  @FXML private Label attachmentsLabel;

  @FXML private HBox downloadsHBox;

  @FXML private Label senderLabel;

  @FXML private Label subjectLabel;

  @FXML private WebView webView;

  public EmailDetailsController(
      EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }
}
