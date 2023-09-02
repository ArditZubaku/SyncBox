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
                             EmailTreeItem<String> foldersRoot) {
    for (Folder folder : folders) {
      EmailTreeItem<String> treeItem = new EmailTreeItem<>(folder.getName());
      foldersRoot.getChildren().add(treeItem);
    }
  }
}
