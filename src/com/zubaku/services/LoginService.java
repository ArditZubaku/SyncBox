package com.zubaku.services;

import com.zubaku.models.EmailAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.utils.LoginResult;
import javafx.concurrent.*;
import javafx.concurrent.Service;
import javax.mail.*;

public class LoginService extends Service<LoginResult> {

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
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(account.getEmail(),
                                          account.getPassword());
      }
    };

    try {

      Thread.sleep(6000);

      Session session =
          Session.getInstance(account.getProperties(), authenticator);
      Store store = session.getStore("imaps");
      store.connect(account.getProperties().getProperty("incomingHost"),
                    account.getEmail(), account.getPassword());
      account.setStore(store);
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
      return LoginResult.FAILED_BY_NETWORK;
    } catch (AuthenticationFailedException e) {
      e.printStackTrace();
      return LoginResult.FAILED_BY_CREDENTIALS;
    } catch (MessagingException e) {
      e.printStackTrace();
      return LoginResult.FAILED_BY_UNEXPECTED_ERROR;
    } catch (Exception e) {
      e.printStackTrace();
      return LoginResult.FAILED_BY_UNEXPECTED_ERROR;
    }
    return LoginResult.SUCCESS;
  }
}
