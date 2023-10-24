package com.zubaku.application;

import com.zubaku.models.EmailAccount;
import com.zubaku.persistence.PersistenceAccess;
import com.zubaku.persistence.ValidAccount;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class Launcher extends Application {
  private PersistenceAccess persistenceAccess = new PersistenceAccess();
  private EmailProcessor emailProcessor = new EmailProcessor();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    HostServices hostServices = getHostServices();
    ViewProcessor viewProcessor = new ViewProcessor(emailProcessor, hostServices);
    viewProcessor.showLoginWindow();
    viewProcessor.updateStyles();
  }

  @Override
  public void stop() throws Exception {
    List<ValidAccount> validAccounts = new ArrayList<>();

    for (EmailAccount emailAccount : emailProcessor.getEmailAccounts()) {
      validAccounts.add(new ValidAccount(emailAccount.getEmail(), emailAccount.getPassword()));
    }

    persistenceAccess.saveToPersistence(validAccounts);
  }
}
