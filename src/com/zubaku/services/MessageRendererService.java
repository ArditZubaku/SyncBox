package com.zubaku.services;

import com.zubaku.models.EmailMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

public class MessageRendererService extends Service<Void> {
  private static final Logger LOGGER =
      Logger.getLogger(MessageRendererService.class.getName());
  private final WebEngine webEngine;
  // Holds the content that will be rendered by the WebEngine
  private final StringBuffer stringBuffer;
  private EmailMessage emailMessage;

  public MessageRendererService(WebEngine webEngine) {
    this.webEngine = webEngine;
    this.stringBuffer = new StringBuffer();
    this.setOnSucceeded(event -> displayEmailContent());
  }

  public void setEmailMessage(EmailMessage emailMessage) {
    this.emailMessage = emailMessage;
  }

  @Override
  protected Task<Void> createTask() {
    return new Task<>() {
      @Override
      protected Void call() {
        try {
          loadEmailContent();
        } catch (Exception e) {
          LOGGER.log(Level.SEVERE, "Failed to render the Email content", e);
        }
        return null;
      }
    };
  }

  private void displayEmailContent() {
    webEngine.loadContent(stringBuffer.toString());
  }

  private void loadEmailContent() throws MessagingException, IOException {
    // Since this method will be called many times,
    // we shall always first clear the buffer
    stringBuffer.setLength(0);
    Message message = emailMessage.getMessage();
    String contentType = message.getContentType();

    if (isSimpleType(contentType)) {
      // For simple content types directly append the text content
      stringBuffer.append(message.getContent().toString());
    } else if (isMultipartType(contentType)) {
      Multipart multipart = (Multipart)message.getContent();
      loadMultipartContent(multipart, stringBuffer);
    }
  }

  private void loadMultipartContent(Multipart multipart,
                                    StringBuffer stringBuffer)
      throws MessagingException, IOException {
    for (int i = 0; i < multipart.getCount(); i++) {
      BodyPart bodyPart = multipart.getBodyPart(i);
      String bodyPartContentType = bodyPart.getContentType();
      if (isSimpleType(bodyPartContentType)) {
        // Append the text content of this part
        stringBuffer.append(bodyPart.getContent().toString());
      } else if (isMultipartType(bodyPartContentType)) {
        Multipart multipart2 = (Multipart)bodyPart.getContent();
        loadMultipartContent(multipart2, stringBuffer);
      } else if (!isPlainText(bodyPartContentType)) {
        // Handle the attachments
        MimeBodyPart mimeBodyPart = (MimeBodyPart)bodyPart;
        emailMessage.addAttachment(mimeBodyPart);

      }
    }
  }

  private boolean isSimpleType(String contentType) {
    return contentType.contains("TEXT/HTML") || contentType.contains("mixed") ||
        contentType.contains("text");
  }

  private boolean isMultipartType(String contentType) {
    return contentType.contains("multipart");
  }

  private boolean isPlainText(String contentType) {
    return contentType.contains("TEXT/PLAIN");
  }
}
