package com.zubaku.controllers;

import com.zubaku.models.EmailMessage;
import com.zubaku.models.EmailSize;
import com.zubaku.models.EmailTreeItem;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.MessageRendererService;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowController
    extends BaseController implements Initializable {

  private final MenuItem markUnreadMenuItem = new MenuItem("Mark as unread");
  private final MenuItem deleteEmailMessageMenuItem =
      new MenuItem("Delete email message");

  @FXML private TableView<EmailMessage> emailsTableView;
  @FXML private TableColumn<EmailMessage, String> senderColumn;
  @FXML private TableColumn<EmailMessage, String> subjectColumn;
  @FXML private TableColumn<EmailMessage, String> recipientColumn;
  @FXML private TableColumn<EmailMessage, EmailSize> sizeColumn;
  @FXML private TableColumn<EmailMessage, Date> dateColumn;

  @FXML private WebView emailWebView;

  @FXML private TreeView<String> emailsTreeView;

  private MessageRendererService messageRendererService;

  public MainWindowController(EmailProcessor emailProcessor,
                              ViewProcessor viewProcessor, String fxmlName) {
    super(emailProcessor, viewProcessor, fxmlName);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setUpEmailsTreeView();
    setUpEmailsTableView();
    setUpSelectedFolder();
    setUpUnreadEmailsAsBold();
    setUpMessageRendererService();
    setUpSelectedEmailMessage();
    setUpContextMenus();
  }

  @FXML
  void optionsAction() {
    viewProcessor.showOptionsWindow();
  }

  @FXML
  void addAccountAction() {
    viewProcessor.showLoginWindow();
  }

  @FXML
  void composeMessageAction() {
    viewProcessor.showComposeMessageWindow();
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

    emailsTableView.setContextMenu(
        new ContextMenu(markUnreadMenuItem, deleteEmailMessageMenuItem));
  }

  private void setUpSelectedFolder() {
    emailsTreeView.setOnMouseClicked(event -> {
      EmailTreeItem<String> folder =
          (EmailTreeItem<String>)emailsTreeView.getSelectionModel()
              .getSelectedItem();
      // Checking for null pointer exception
      if (folder != null) {
        emailProcessor.setSelectedFolder(folder);
        emailsTableView.setItems(folder.getEmailMessages());
      }
    });
  }

  private void setUpUnreadEmailsAsBold() {
    emailsTableView.setRowFactory(new Callback<>() {
      @Override
      public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
        return new TableRow<>() {
          @Override
          protected void updateItem(EmailMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
              if (item.isRead()) {
                setStyle("");
              } else {
                setStyle("-fx-font-weight: bold");
              }
            }
          }
        };
      }
    });
  }

  private void setUpMessageRendererService() {
    messageRendererService =
        new MessageRendererService(emailWebView.getEngine());
  }

  private void setUpSelectedEmailMessage() {
    emailsTableView.setOnMouseClicked(event -> {
      EmailMessage emailMessage =
          emailsTableView.getSelectionModel().getSelectedItem();
      if (emailMessage != null) {
        emailProcessor.setSelectedEmailMessage(emailMessage);

        if (!emailMessage.isRead()) {
          emailProcessor.setRead();
        }

        messageRendererService.setEmailMessage(emailMessage);
        // Start method can be called only once, that's why we call the restart
        // method
        messageRendererService.restart();
      }
    });
  }

  private void setUpContextMenus() {
    markUnreadMenuItem.setOnAction(event -> { emailProcessor.setUnread(); });
    deleteEmailMessageMenuItem.setOnAction(event -> {
      emailProcessor.deleteSelectedEmailMessage();
      emailWebView.getEngine().loadContent("");
    });
  }
}