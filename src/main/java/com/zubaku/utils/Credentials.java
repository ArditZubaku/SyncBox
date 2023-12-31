package com.zubaku.utils;

public enum Credentials {
  EMAIL_ADDRESS(""),
  PASSWORD("");

  public static String EMAIL_ADDRESS() {
    return Credentials.EMAIL_ADDRESS.credential;
  }

  public static String PASSWORD() {
    return Credentials.PASSWORD.credential;
  }

  private final String credential;

  Credentials(String credential) {
    this.credential = credential;
  }
}
