package com.zubaku.processors;

import com.zubaku.models.EmailAccount;
import com.zubaku.models.EmailMessage;
import com.zubaku.models.EmailTreeItem;
import com.zubaku.services.FetchFoldersService;
import com.zubaku.services.FolderUpdaterService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;

// To manage email actions
// Folder handling
public class EmailProcessor {
  private static final Logger LOGGER =
      Logger.getLogger(FolderUpdaterService.class.getName());

  // To update the view we need to know what was selected
  private EmailMessage selectedEmailMessage;
  private EmailTreeItem<String> selectedFolder;

  private final EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

  public EmailTreeItem<String> getFoldersRoot() { return foldersRoot; }

  // List of all the folders of all accounts
  private final List<Folder> foldersList = new ArrayList<>();

  public List<Folder> getFoldersList() { return foldersList; }

  public EmailProcessor() {
    FolderUpdaterService folderUpdaterService =
        new FolderUpdaterService(foldersList);
    folderUpdaterService.start();
  }

  public EmailMessage getSelectedEmailMessage() { return selectedEmailMessage; }

  public void setSelectedEmailMessage(EmailMessage selectedEmailMessage) {
    this.selectedEmailMessage = selectedEmailMessage;
  }

  public EmailTreeItem<String> getSelectedFolder() { return selectedFolder; }

  public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
    this.selectedFolder = selectedFolder;
  }

  public void addEmailAccount(EmailAccount account) {
    EmailTreeItem<String> treeItem = new EmailTreeItem<>(account.getEmail());
    FetchFoldersService service =
        new FetchFoldersService(account.getStore(), treeItem, foldersList);
    service.start();
    foldersRoot.getChildren().add(treeItem);
  }

  public void setRead() {
    try {
      selectedEmailMessage.setRead(true);
      selectedEmailMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
      selectedFolder.decrementUnreadMessagesCount();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error setting message to read", e);
    }
  }

  public void setUnread() {
    try {
      selectedEmailMessage.setRead(false);
      selectedEmailMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
      selectedFolder.incrementUnreadMessagesCount();
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error setting message to unread", e);
    }
  }

  public void deleteSelectedEmailMessage() {
    try {
      selectedEmailMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
//      selectedEmailMessage.getMessage().getFolder().expunge();
      selectedFolder.getEmailMessages().remove(selectedEmailMessage);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error deleting selected email message", e);
    }
  }
}
