package com.zubaku.services;

import static com.zubaku.utils.Constants.*;

import com.zubaku.models.EmailAccount;
import com.zubaku.utils.EmailSendingResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService extends Service<EmailSendingResult> {
  private EmailAccount account;
  private String subject;
  private String recipient;
  private String content;

  public EmailSenderService(EmailAccount account, String subject,
                            String recipient, String content) {
    this.account = account;
    this.subject = subject;
    this.recipient = recipient;
    this.content = content;
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

          // Send the email message
          Transport transport = account.getSession().getTransport();
          transport.connect(
              account.getProperties().getProperty(OutgoingHost.getValue()),
              account.getEmail(), account.getPassword());
          transport.sendMessage(message, message.getAllRecipients());
          transport.close();

          return EmailSendingResult.SUCCESS;
        } catch (MessagingException e) {
          return EmailSendingResult.FAILED_BY_PROVIDER;
        } catch (Exception e) {
          return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
        }
      }
    };
  }
}
