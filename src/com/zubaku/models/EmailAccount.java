package com.zubaku.models;

import java.util.Properties;
import javax.mail.Store;

public class EmailAccount {
  private final String email;
  private final String password;
  private Properties properties;
  // For storing and retrieving messages
  private Store store;

  public EmailAccount(String email, String password) {
    this.email = email;
    this.password = password;
    properties = new Properties();
    properties.put("incomingHost", "imap.gmail.com");
    properties.put("mail.store.protocol", "imaps");

    properties.put("mail.transport.protocol", "smtps");
    properties.put("mail.smtps.host", "smtp.gmail.com");
    properties.put("mail.smtps.auth", "true");
    properties.put("outgoingHost", "smtp.gmail.com");
  }

  public String getEmail() { return email; }

  public String getPassword() { return password; }

  public Properties getProperties() { return properties; }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public Store getStore() { return store; }

  public void setStore(Store store) { this.store = store; }

  @Override
  public String toString() {
    return getEmail();
  }
}
