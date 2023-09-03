package com.zubaku.services;

import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;

public class FolderUpdaterService extends Service<Void> {
  // This service will start as our program starts and will run the whole time
  // Bc we will need to call it to search for new messages added or removed
  // events

  private List<Folder> foldersList;

  public FolderUpdaterService(List<Folder> foldersList) {
    this.foldersList = foldersList;
  }

  @Override
  protected Task<Void> createTask() {
    return new Task<>() {
      @Override
      protected Void call() throws Exception {
        for (;;) {
          try {
            // Check for new events every 5 seconds
            Thread.sleep(5000);
            for (Folder folder : foldersList) {
                if (folder.getType()!=Folder.HOLDS_FOLDERS && folder.isOpen()){
                    folder.getMessageCount();
                }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    };
  }
}
