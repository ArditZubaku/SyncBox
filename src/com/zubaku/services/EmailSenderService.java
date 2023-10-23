package com.zubaku.services;

import static com.zubaku.utils.Constants.*;

import com.zubaku.models.EmailAccount;
import com.zubaku.utils.EmailSendingResult;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService extends Service<EmailSendingResult> {
  private static final Logger LOGGER = Logger.getLogger(EmailSenderService.class.getName());

  private final EmailAccount account;
  private final String subject;
  private final String recipient;
  private final String content;
  private final List<File> attachments;

  public EmailSenderService(
      EmailAccount account,
      String subject,
      String recipient,
      String content,
      List<File> attachments) {
    this.account = account;
    this.subject = subject;
    this.recipient = recipient;
    this.content = content;
    this.attachments = attachments;
  }

  @Override
  protected Task<EmailSendingResult> createTask() {
    return new Task<>() {
      @Override
      protected EmailSendingResult call() {
        try {
          // Create the email message
          MimeMessage message = new MimeMessage(account.getSession());
          message.setFrom(account.getEmail());
          message.setRecipients(Message.RecipientType.TO, recipient);
          message.setSubject(subject);

          // Set the content
          Multipart multipart = new MimeMultipart();
          BodyPart messageBody = new MimeBodyPart();
          /*"com.sun.mail.handlers.text_html"*/
          messageBody.setContent(content, "text/html");
          multipart.addBodyPart(messageBody);
          message.setContent(multipart);

          // Add the attachments
          if (!attachments.isEmpty()) {
            // TODO: Show the selected attachments
            for (File file : attachments) {
              MimeBodyPart mimeBodyPart = new MimeBodyPart();
              DataSource source = new FileDataSource(file.getAbsolutePath());
              mimeBodyPart.setDataHandler(new DataHandler(source));
              mimeBodyPart.setFileName(file.getName());

              multipart.addBodyPart(mimeBodyPart);
            }
          }

          // Send the email message
          Transport transport = account.getSession().getTransport();
          transport.connect(
              account.getProperties().getProperty(OutgoingHost.getValue()),
              account.getEmail(),
              account.getPassword());
          transport.sendMessage(message, message.getAllRecipients());
          transport.close();

          return EmailSendingResult.SUCCESS;
        } catch (MessagingException e) {
          LOGGER.log(Level.SEVERE, "Failed by provider!", e);
          return EmailSendingResult.FAILED_BY_PROVIDER;
        } catch (Exception e) {
          LOGGER.log(Level.SEVERE, "Failed by unexpected error!", e);
          return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
        }
      }
    };
  }
}
