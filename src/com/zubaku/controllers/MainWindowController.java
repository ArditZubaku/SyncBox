package com.zubaku.controllers;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController
    extends BaseController implements Initializable {

  @FXML private TableView<?> emailTableView;

  @FXML private WebView emailWebView;

  @FXML private TreeView<String> emailsTreeView;

  public MainWindowController(EmailProcessor emailProcessor,
                              ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setUpEmailsTreeView();
  }

  @FXML
  void optionsAction() {
    viewProcessor.showOptionsWindow();
  }

  @FXML
  void addAccountAction() {
    viewProcessor.showLoginWindow();
  }

  private void setUpEmailsTreeView() {
    emailsTreeView.setRoot(emailProcessor.getFoldersRoot());
    // Hide the root since it is just an empty element
    emailsTreeView.setShowRoot(false);
  }
}