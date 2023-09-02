package com.zubaku.processors;

import com.zubaku.models.EmailAccount;
import javafx.scene.control.TreeItem;

public class EmailProcessor {
  // To manage email actions
  // Folder handling

  private TreeItem<String> foldersRoot = new TreeItem<String>("");

  public TreeItem<String> getFoldersRoot() { return foldersRoot; }

  public void addEmailAccount(EmailAccount account) {
    TreeItem<String> treeItem = new TreeItem<String>(account.getEmail());
    treeItem.setExpanded(true);
    treeItem.getChildren().add(new TreeItem<String>("INBOX"));
    treeItem.getChildren().add(new TreeItem<String>("SENT"));
    treeItem.getChildren().add(new TreeItem<String>("SPAM"));
    foldersRoot.getChildren().add(treeItem);
  }
}
