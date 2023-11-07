package com.zubaku.services;

import com.zubaku.models.EmailAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.utils.LoginResult;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<LoginResult> {

  private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

  EmailAccount account;
  EmailProcessor processor;

  public LoginService(EmailAccount account, EmailProcessor processor) {
    this.account = account;
    this.processor = processor;
  }

  @Override
  protected Task<LoginResult> createTask() {
    return new Task<LoginResult>() {
      @Override
      protected LoginResult call() throws Exception {
        return login();
      }
    };
  }

  private LoginResult login() {
    Authenticator authenticator =
        new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(account.getEmail(), account.getPassword());
          }
        };

    try {
      Properties properties = account.getProperties();
      properties.put("mail.smtps.ssl.trust", "*");
      Session session = Session.getInstance(properties, authenticator);

      account.setSession(session);
      Store store = session.getStore("imaps");
      store.connect(
          account.getProperties().getProperty("incomingHost"),
          account.getEmail(),
          account.getPassword());

      account.setStore(store);
      processor.addEmailAccount(account);
    } catch (NoSuchProviderException e) {
      LOGGER.log(Level.SEVERE, "FAILED_BY_NETWORK", e);
      return LoginResult.FAILED_BY_NETWORK;
    } catch (AuthenticationFailedException e) {
      LOGGER.log(Level.SEVERE, "FAILED_BY_CREDENTIALS", e);
      return LoginResult.FAILED_BY_CREDENTIALS;
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "FAILED_BY_UNEXPECTED_ERROR", e);
      return LoginResult.FAILED_BY_UNEXPECTED_ERROR;
    }
    return LoginResult.SUCCESS;
  }
}
