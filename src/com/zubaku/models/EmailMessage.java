package com.zubaku.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;

public class EmailMessage {

  private final SimpleStringProperty subject;
  private final SimpleStringProperty sender;
  private final SimpleStringProperty recipient;
  private final SimpleObjectProperty<EmailSize> size;
  private final SimpleObjectProperty<Date> date;
  private final Message message;
  private final List<MimeBodyPart> attachmentsList = new ArrayList<>();
  private boolean isRead;
  private boolean hasAttachments = false;

  public EmailMessage(
      String subject,
      String sender,
      String recipient,
      int size,
      Date date,
      boolean isRead,
      Message message) {
    this.subject = new SimpleStringProperty(subject);
    this.sender = new SimpleStringProperty(sender);
    this.recipient = new SimpleStringProperty(recipient);
    this.size = new SimpleObjectProperty<>(new EmailSize(size));
    this.date = new SimpleObjectProperty<>(date);
    this.isRead = isRead;
    this.message = message;
  }

  public List<MimeBodyPart> getAttachmentsList() {
    return attachmentsList;
  }

  public boolean hasAttachments() {
    return hasAttachments;
  }

  public String getSubject() {
    return this.subject.get();
  }

  public String getSender() {
    return this.sender.get();
  }

  public String getRecipient() {
    return this.recipient.get();
  }

  public EmailSize getSize() {
    return this.size.get();
  }

  public Date getDate() {
    return this.date.get();
  }

  public boolean isRead() {
    return this.isRead;
  }

  public void setRead(boolean read) {
    this.isRead = read;
  }

  public Message getMessage() {
    return this.message;
  }

  public void addAttachment(MimeBodyPart mimeBodyPart) {
    hasAttachments = true;
    attachmentsList.add(mimeBodyPart);
  }
}
