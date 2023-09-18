package com.zubaku.utils;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {
  private static final Logger LOGGER =
      Logger.getLogger(IconResolver.class.getName());

  // Constants for icon file names
  private static final String EMAIL_ICON = "email.png";
  private static final String INBOX_ICON = "inbox.png";
  private static final String GMAIL_ICON = "gmail.png";
  private static final String OUTLOOK_ICON = "outlook.png";
  private static final String SENT_ICON = "sent2.png";
  private static final String SPAM_ICON = "spam.png";
  private static final String BIN_ICON = "bin.png";
  private static final String FOLDER_ICON = "folder.png";

  public Node getIconForFolder(String folderName) {
    String folderNameLowerCase = folderName.toLowerCase();

    String iconFileName = getIconFileName(folderNameLowerCase);

    try (InputStream inputStream = getClass().getResourceAsStream(
             Paths.IconsPackage + iconFileName)) {
      if (inputStream != null) {
        return new ImageView(new Image(inputStream, 25, 25, true, true));
      } else {
        LOGGER.log(Level.SEVERE,
                   "Icon not found for folder: " + folderNameLowerCase);
        return null;
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE,
                 "Error getting icon for folder: " + folderNameLowerCase, e);
      return null;
    }
  }

  private static String getIconFileName(String folderNameLowerCase) {
    String iconFileName = FOLDER_ICON;

    // Update the icon file name based on folderNameLowerCase
    if (folderNameLowerCase.contains("@")) {
      iconFileName = EMAIL_ICON;
    } else if (folderNameLowerCase.contains("inbox")) {
      iconFileName = INBOX_ICON;
    } else if (folderNameLowerCase.contains("gmail")) {
      iconFileName = GMAIL_ICON;
    } else if (folderNameLowerCase.contains("outlook")) {
      iconFileName = OUTLOOK_ICON;
    } else if (folderNameLowerCase.contains("sent")) {
      iconFileName = SENT_ICON;
    } else if (folderNameLowerCase.contains("spam")) {
      iconFileName = SPAM_ICON;
    } else if (folderNameLowerCase.contains("bin")) {
      iconFileName = BIN_ICON;
    }
    return iconFileName;
  }
}
