package com.zubaku.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {
  private String name;
  private ObservableList<EmailMessage> emailMessages;
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
    boolean isRead = message.getFlags().contains(Flags.Flag.SEEN);

    EmailMessage emailMessage = new EmailMessage(
        message.getSubject(), message.getFrom()[0].toString(),
        message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
        message.getSize(), message.getSentDate(), isRead, message);

    emailMessages.add(emailMessage);

    if (!isRead)
      incrementUnreadMessagesCount();

    System.out.println("Added email to folder: " + name + " - "
                       + "Subject: " + emailMessage.getSubject());
  }

  private void updateName() {
    if (unreadMessages > 0) {
      this.setValue((String) (name + "(" + unreadMessages + ")"));
    } else {
      this.setValue(name);
    }
  }

  private void incrementUnreadMessagesCount() {
    unreadMessages++;
    updateName();
  }
}
