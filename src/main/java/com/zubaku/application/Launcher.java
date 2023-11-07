package com.zubaku.application;

import com.zubaku.models.EmailAccount;
import com.zubaku.persistence.PersistenceAccess;
import com.zubaku.persistence.ValidAccount;

import java.util.ArrayList;
import java.util.List;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.services.LoginService;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class Launcher extends Application {
  private final PersistenceAccess persistenceAccess = new PersistenceAccess();
  private final EmailProcessor emailProcessor = new EmailProcessor();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {

    HostServices hostServices = getHostServices();
    ViewProcessor viewProcessor = new ViewProcessor(emailProcessor, hostServices);
    List<ValidAccount> validAccounts = persistenceAccess.loadFromPersistence();
    if (!validAccounts.isEmpty()) {
      viewProcessor.showMainWindow();
      for (ValidAccount validAccount : validAccounts) {
        EmailAccount emailAccount =
            new EmailAccount(validAccount.getEmailAddress(), validAccount.getPassword());
        LoginService loginService = new LoginService(emailAccount, emailProcessor);
        loginService.start();
      }
    } else {
      viewProcessor.showLoginWindow();
    }
  }

  @Override
  public void stop() {
    List<ValidAccount> validAccounts = new ArrayList<>();

    for (EmailAccount emailAccount : emailProcessor.getEmailAccounts()) {
      validAccounts.add(new ValidAccount(emailAccount.getEmail(), emailAccount.getPassword()));
    }

    persistenceAccess.saveToPersistence(validAccounts);
  }
}
