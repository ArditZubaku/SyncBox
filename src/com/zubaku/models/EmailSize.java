package com.zubaku.models;

public class EmailSize implements Comparable<EmailSize> {
  private int size;

  public EmailSize(int size) { this.size = size; }

  @Override
  public String toString() {
    if (size <= 0) {
      return "0";
    } else if (size < 1024) {
      return size + " B";
    } else if (size < (1024 * 1024)) {
      return size / 1024 + " kB";
    } else {
      return size / (1024 * 1024) + " MB";
    }
  }

  @Override
  public int compareTo(EmailSize o) {
    return Integer.compare(size, o.size);
  }
}