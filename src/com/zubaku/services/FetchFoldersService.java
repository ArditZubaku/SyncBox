package com.zubaku.services;

import com.zubaku.models.EmailTreeItem;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class FetchFoldersService extends Service<Void> {

  private Store store;
  private EmailTreeItem<String> foldersRoot;
  private List<Folder> foldersList;

  public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot,
                             List<Folder> foldersList) {
    this.store = store;
    this.foldersRoot = foldersRoot;
    this.foldersList = foldersList;
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
      foldersList.add(folder);
      EmailTreeItem<String> treeItem = new EmailTreeItem<>(folder.getName());
      foldersRoot.getChildren().add(treeItem);
      foldersRoot.setExpanded(true);

      fetchEmailMessagesOnFolder(folder, treeItem);
      addMessageListenerToFolder(folder, treeItem);

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
                  treeItem.addEmail(folder.getMessage(i));
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

  private void addMessageListenerToFolder(Folder folder,
                                          EmailTreeItem<String> treeItem) {
    folder.addMessageCountListener(new MessageCountListener() {
      @Override
      public void messagesAdded(MessageCountEvent messageCountEvent) {
        for (int i = 0; i < messageCountEvent.getMessages().length ; i++) {
          try {
            // Getting the latest messages
            Message message = folder.getMessage(folder.getMessageCount() - i);
            // Adding them to the top of the TreeView
            treeItem.addEmailToTop(message);
          } catch (MessagingException e) {
            throw new RuntimeException(e);
          }
        }
      }

      @Override
      public void messagesRemoved(MessageCountEvent messageCountEvent) {
        System.out.println("Message removed event: " + messageCountEvent);
      }
    });
  }
}
