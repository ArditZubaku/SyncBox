package com.zubaku.services;

import com.zubaku.models.EmailAccount;
import com.zubaku.utils.EmailSendingResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

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
      protected EmailSendingResult call() throws Exception {
        return null;
      }
    };
  }
}
