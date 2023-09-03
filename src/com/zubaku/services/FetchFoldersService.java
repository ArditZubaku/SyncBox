package com.zubaku.services;

import com.zubaku.models.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchFoldersService extends Service<Void> {

  private Store store;
  private EmailTreeItem<String> foldersRoot;

  public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot) {
    this.store = store;
    this.foldersRoot = foldersRoot;
  }

  @Override
  protected Task<Void> createTask() {
    return new Task<>() {
      @Override
      protected Void call() throws Exception {
        fetchFolders();
        return null;
      }
    };
  }

  private void fetchFolders() throws MessagingException {
    // Get the folders from the store
    Folder[] folders = store.getDefaultFolder().list();
    // Handle the folders
    handleFolders(folders, foldersRoot);
  }

  private void handleFolders(Folder[] folders,
                             EmailTreeItem<String> foldersRoot)
      throws MessagingException {
    for (Folder folder : folders) {
      EmailTreeItem<String> treeItem = new EmailTreeItem<>(folder.getName());
      foldersRoot.getChildren().add(treeItem);
      foldersRoot.setExpanded(true);

      fetchEmailMessagesOnFolder(folder, treeItem);

      if (folder.getType() == Folder.HOLDS_FOLDERS) {
        Folder[] subFolders = folder.list();
        // Recursively -> calling itself
        handleFolders(subFolders, treeItem);
      }
    }
  }

  private void fetchEmailMessagesOnFolder(Folder folder,
                                          EmailTreeItem<String> treeItem) {
    Service<Void> fetchMessagesService = new Service<>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<>() {
          @Override
          protected Void call() throws MessagingException {
            if (folder.getType() != Folder.HOLDS_FOLDERS) {
              try (folder) {
                folder.open(Folder.READ_WRITE);
                int folderSize = folder.getMessageCount();
                for (int i = folderSize; i > 0; i--) {
                  String subject = folder.getMessage(i).getSubject();
                  System.out.println(subject);
                }
                // Try with resources
                // Automatically closes the folder when done
              }
            }
            return null;
          }
        };
      }
    };
    fetchMessagesService.start();
  }
}
