package com.zubaku.utils;

public enum Paths {
  ViewPackage("/com/zubaku/views/"),
  IconsPackage("/com/zubaku/views/icons/"),

  ThemeLIGHT("css/themeLight.css"),
  ThemeDARK("css/themeDark.css"),
  ThemeDEFAULT("css/themeDefault.css"),

  FontSMALL("css/fontSmall.css"),
  FontBIG("css/fontBig.css"),
  FontMEDIUM("css/fontMedium.css");

  public static String getThemeCSSPath(ColorTheme theme) {
    return switch (theme) {
      case LIGHT -> ViewPackage.path + ThemeLIGHT;
      case DARK -> ViewPackage.path + ThemeDARK;
      case DEFAULT -> ViewPackage.path + ThemeDEFAULT;
    };
  }

  public static String getFontCSSPath(FontSize size) {
    return switch (size) {
      case SMALL -> ViewPackage.path + FontSMALL;
      case BIG -> ViewPackage.path + FontBIG;
      case MEDIUM -> ViewPackage.path + FontMEDIUM;
    };
  }

  private final String path;

  Paths(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return path;
  }
}
