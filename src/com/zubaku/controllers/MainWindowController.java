package com.zubaku.controllers;

import com.zubaku.models.EmailMessage;
import com.zubaku.models.EmailTreeItem;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

public class MainWindowController
    extends BaseController implements Initializable {

  @FXML private TableView<EmailMessage> emailsTableView;
  @FXML private TableColumn<EmailMessage, String> senderColumn;
  @FXML private TableColumn<EmailMessage, String> subjectColumn;
  @FXML private TableColumn<EmailMessage, String> recipientColumn;
  @FXML private TableColumn<EmailMessage, Integer> sizeColumn;
  @FXML private TableColumn<EmailMessage, Date> dateColumn;

  @FXML private WebView emailWebView;

  @FXML private TreeView<String> emailsTreeView;

  public MainWindowController(EmailProcessor emailProcessor,
                              ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setUpEmailsTreeView();
    setUpEmailsTableView();
    setUpSelectedFolder();
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

  private void setUpEmailsTableView() {
    senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
    subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
    recipientColumn.setCellValueFactory(
        new PropertyValueFactory<>("recipient"));
    sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
  }

  private void setUpSelectedFolder() {
    emailsTreeView.setOnMouseClicked(event -> {
      EmailTreeItem<String> treeItem =
          (EmailTreeItem<String>)emailsTreeView.getSelectionModel()
              .getSelectedItem();
      // Checking for null pointer exception
      if (treeItem != null) {
        emailsTableView.setItems(treeItem.getEmailMessages());
      }
    });
  }
}