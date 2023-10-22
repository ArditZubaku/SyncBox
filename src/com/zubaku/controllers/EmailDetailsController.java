package com.zubaku.controllers;

import com.zubaku.models.EmailMessage;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.MessageRendererService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class EmailDetailsController extends BaseController implements Initializable {
  private static final Logger LOGGER = Logger.getLogger(EmailDetailsController.class.getName());
  private final String DOWNLOADS_FOLDER = System.getProperty("user.home") + "/Downloads/SyncBox/";

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

    loadAttachments(emailMessage);

    MessageRendererService service = new MessageRendererService(webView.getEngine());
    service.setEmailMessage(emailMessage);
    service.restart();
  }

  private void mkDirIfNotExists() {
    File file = new File(DOWNLOADS_FOLDER);
    if (!file.exists()) file.mkdir();
  }

  private void loadAttachments(EmailMessage emailMessage) {
    if (emailMessage.hasAttachments()) {
      for (MimeBodyPart mimeBodyPart : emailMessage.getAttachmentsList()) {
        try {
          AttachmentButton button = new AttachmentButton(mimeBodyPart);
          downloadsHBox.getChildren().add(button);
        } catch (MessagingException e) {
          LOGGER.log(Level.SEVERE, "Error loading attachments");
        }
      }
    } else {
      attachmentsLabel.setText("");
    }
  }

  private class AttachmentButton extends Button {
    private final MimeBodyPart mimeBodyPart;
    private final String downloadedFilePath;

    public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException {
      this.mimeBodyPart = mimeBodyPart;
      this.setText(mimeBodyPart.getFileName());
      this.downloadedFilePath = DOWNLOADS_FOLDER + mimeBodyPart.getFileName();
      this.setOnAction(event -> downloadAttachment());
    }

    private void downloadAttachment() {
      // Since this might be a long going task, we need to put it in a separate thread
      Service<Void> service =
          new Service<>() {
            @Override
            protected Task<Void> createTask() {
              return new Task<>() {
                @Override
                protected Void call() throws Exception {
                  mkDirIfNotExists();
                  mimeBodyPart.saveFile(downloadedFilePath);
                  return null;
                }
              };
            }
          };
      service.restart();
    }
  }
}
