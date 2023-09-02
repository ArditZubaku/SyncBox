package com.zubaku.processors;

import com.zubaku.models.EmailAccount;
import com.zubaku.models.EmailTreeItem;
import com.zubaku.services.FetchFoldersService;

public class EmailProcessor {
  // To manage email actions
  // Folder handling

  private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

  public EmailTreeItem<String> getFoldersRoot() { return foldersRoot; }

  public void addEmailAccount(EmailAccount account) {
    EmailTreeItem<String> treeItem = new EmailTreeItem<>(account.getEmail());
    FetchFoldersService service =
        new FetchFoldersService(account.getStore(), treeItem);
    service.start();
    foldersRoot.getChildren().add(treeItem);
  }
}
