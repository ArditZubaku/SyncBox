package com.zubaku.utils;

import java.util.Base64;

public class Encoder {

  private static Base64.Encoder encoder = Base64.getEncoder();
  private static Base64.Decoder decoder = Base64.getDecoder();

  public String encode(String text) {
    return encoder.encodeToString(text.getBytes());
  }

  public String decode(String text) {
    return new String(decoder.decode(text.getBytes()));
  }
}
