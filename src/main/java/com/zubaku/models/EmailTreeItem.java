package com.zubaku.models;

import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {
  private final String name;
  private final ObservableList<EmailMessage> emailMessages;
  private int unreadMessages;

  public EmailTreeItem(String name) {
    super(name);
    this.name = name;
    this.emailMessages = FXCollections.observableArrayList();
  }

  public ObservableList<EmailMessage> getEmailMessages() {
    return emailMessages;
  }

  public void addEmail(Message message) throws MessagingException {
    EmailMessage emailMessage = fetchMessage(message);
    emailMessages.add(emailMessage);
  }

  public void addEmailToTop(Message message) throws MessagingException {
    EmailMessage emailMessage = fetchMessage(message);
    emailMessages.add(0, emailMessage);
  }

  private EmailMessage fetchMessage(Message message) throws MessagingException {
    boolean isRead = message.getFlags().contains(Flags.Flag.SEEN);

    EmailMessage emailMessage =
        new EmailMessage(
            message.getSubject(),
            message.getFrom()[0].toString(),
            message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
            message.getSize(),
            message.getSentDate(),
            isRead,
            message);

    if (!isRead) incrementUnreadMessagesCount();

    return emailMessage;
  }

  private void updateName() {
    if (unreadMessages > 0) {
      this.setValue((String) (name + "(" + unreadMessages + ")"));
    } else {
      this.setValue(name);
    }
  }

  public void incrementUnreadMessagesCount() {
    unreadMessages++;
    updateName();
  }

  // After reading messages -> decrement unread messages count
  public void decrementUnreadMessagesCount() {
    unreadMessages--;
    updateName();
  }
}
