package com.zubaku.utils;

public enum Constants {
  IncomingHost("incomingHost"),
  IncomingHostValue("imap.gmail.com"),
  MailStoreProtocol("mail.store.protocol"),
  MailStoreProtocolValue("imaps"), // Corrected typo to "imaps"
  MailTransportProtocol("mail.transport.protocol"),
  MailTransportProtocolValue("smtps"),
  MailSmtpsHost("mail.smtps.host"),
  MailSmtpsHostValue("smtp.gmail.com"),
  MailSmtpsAuth("mail.smtps.auth"),
  MailSmtpsAuthValue("true"),
  OutgoingHost("outgoingHost"),
  OutgoingHostValue("smtp.gmail.com");

  private final String value;

  Constants(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
