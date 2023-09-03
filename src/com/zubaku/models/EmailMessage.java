package com.zubaku.models;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.mail.Message;

public class EmailMessage {

  private SimpleStringProperty subject;
  private SimpleStringProperty sender;
  private SimpleStringProperty recipient;
  private SimpleObjectProperty<EmailSize> size;
  private SimpleObjectProperty<Date> date;
  private boolean isRead;
  private Message message;

  public EmailMessage(String subject, String sender, String recipient, int size,
                      Date date, boolean isRead, Message message) {
    this.subject = new SimpleStringProperty(subject);
    this.sender = new SimpleStringProperty(sender);
    this.recipient = new SimpleStringProperty(recipient);
    this.size = new SimpleObjectProperty<>(new EmailSize(size));
    this.date = new SimpleObjectProperty<>(date);
    this.isRead = isRead;
    this.message = message;
  }

  public String getSubject() { return this.subject.get(); }

  public String getSender() { return this.sender.get(); }

  public String getRecipient() { return this.recipient.get(); }

  public EmailSize getSize() { return this.size.get(); }

  public Date getDate() { return this.date.get(); }

  public boolean isRead() { return this.isRead; }

  public void setRead(boolean read) { this.isRead = read; }

  public Message getMessage() { return this.message; }
}
