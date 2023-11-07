package com.zubaku.persistence;

import com.zubaku.utils.Encoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceAccess {
  private static final Logger LOGGER = Logger.getLogger(PersistenceAccess.class.getName());
  private static final String VALID_ACCOUNTS_LOCATION =
      System.getProperty("user.home") + File.separator + ".validAccounts.ser";
  private final Encoder encoder = new Encoder();

  // Called on program startup
  public List<ValidAccount> loadFromPersistence() {
    List<ValidAccount> accounts = new ArrayList<>();
    File file = new File(VALID_ACCOUNTS_LOCATION);

    if (file.exists()) {
      try (FileInputStream fis = new FileInputStream(file);
          ObjectInputStream ois = new ObjectInputStream(fis)) {

        Object obj = ois.readObject();
        if (obj instanceof List) {
          @SuppressWarnings("unchecked") // Safe to cast after instance check
          List<ValidAccount> persistedList = (List<ValidAccount>) obj;
          decodePasswords(persistedList);
          accounts.addAll(persistedList);
        }
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error loading credentials from persistence.", e);
      }
    }
    return accounts;
  }

  private void decodePasswords(List<ValidAccount> persistedList) {
    for (ValidAccount validAccount : persistedList) {
      String originalPassword = validAccount.getPassword();
      validAccount.setPassword(encoder.decode(originalPassword));
    }
  }

  private void encodePasswords(List<ValidAccount> persistedList) {
    for (ValidAccount validAccount : persistedList) {
      String originalPassword = validAccount.getPassword();
      validAccount.setPassword(encoder.encode(originalPassword));
    }
  }

  // Called on program stop
  public void saveToPersistence(List<ValidAccount> validAccounts) {
    File file = new File(VALID_ACCOUNTS_LOCATION);
    try (FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      encodePasswords(validAccounts);
      oos.writeObject(validAccounts);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error saving credentials to persistence.", e);
    }
  }
}
