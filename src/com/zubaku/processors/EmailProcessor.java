package com.zubaku.processors;

import com.zubaku.models.EmailAccount;
import com.zubaku.models.EmailTreeItem;
import com.zubaku.services.FetchFoldersService;
import com.zubaku.services.FolderUpdaterService;

import java.util.ArrayList;
import java.util.List;
import javax.mail.Folder;

public class EmailProcessor {
  // To manage email actions
  // Folder handling

  private FolderUpdaterService folderUpdaterService;

  private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

  public EmailTreeItem<String> getFoldersRoot() { return foldersRoot; }

  // List of all the folders of all accounts
  private List<Folder> foldersList = new ArrayList<>();

  public List<Folder> getFoldersList() { return foldersList; }

  public EmailProcessor() {
    this.folderUpdaterService = new FolderUpdaterService(foldersList);
    this.folderUpdaterService.start();
  }

  public void addEmailAccount(EmailAccount account) {
    EmailTreeItem<String> treeItem = new EmailTreeItem<>(account.getEmail());
    FetchFoldersService service =
        new FetchFoldersService(account.getStore(), treeItem, foldersList);
    service.start();
    foldersRoot.getChildren().add(treeItem);
  }
}
