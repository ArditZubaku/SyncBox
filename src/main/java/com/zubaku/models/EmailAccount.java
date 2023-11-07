package com.zubaku.models;

import jakarta.mail.Session;
import jakarta.mail.Store;

import static com.zubaku.utils.Constants.*;

import java.util.Properties;

public class EmailAccount {
  private final String email;
  private final String password;
  private Properties properties;
  // For storing and retrieving messages
  private Store store;
  private Session session;

  public EmailAccount(String email, String password) {
    this.email = email;
    this.password = password;
    properties = new Properties();
    properties.put(IncomingHost.getValue(), IncomingHostValue.getValue());
    properties.put(MailStoreProtocol.getValue(), MailStoreProtocolValue.getValue());
    properties.put(MailTransportProtocol.getValue(), MailTransportProtocolValue.getValue());
    properties.put(MailSmtpsHost.getValue(), MailSmtpsHostValue.getValue());
    properties.put(MailSmtpsAuth.getValue(), MailSmtpsAuthValue.getValue());
    properties.put(OutgoingHost.getValue(), OutgoingHostValue.getValue());
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  @Override
  public String toString() {
    return getEmail();
  }
}
