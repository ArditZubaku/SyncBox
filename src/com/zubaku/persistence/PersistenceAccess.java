package com.zubaku.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceAccess {
  private static final Logger LOGGER = Logger.getLogger(PersistenceAccess.class.getName());
  private static final String VALID_ACCOUNTS_LOCATION =
      Optional.ofNullable(System.getenv("APP_DATA"))
              .orElseThrow(
                  () -> new IllegalStateException("APP_DATA environment variable is not set"))
          + "\\validAccounts.ser";

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
          accounts.addAll(persistedList);
        }
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error loading credentials from persistence.", e);
      }
    }
    return accounts;
  }

  public void saveToPersistence(List<ValidAccount> validAccounts) {
    File file = new File(VALID_ACCOUNTS_LOCATION);
    try (FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {

      oos.writeObject(validAccounts);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error saving credentials to persistence.", e);
    }
  }
}
