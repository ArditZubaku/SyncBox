package com.zubaku.persistence;

import java.io.Serializable;

// Marker interface
public class ValidAccount implements Serializable {
  private String emailAddress;
  private String password;

  public ValidAccount(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
