package com.zubaku.processors;

import com.zubaku.controller.BaseController;
import com.zubaku.controller.LoginWindowController;
import com.zubaku.processors.EmailProcessor;
import com.zubaku.utils.FXMLFile;
import com.zubaku.utils.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewProcessor {
    // To generate scenes
    // User actions like close a scene or something
    // To update CSS
    // etc

    private EmailProcessor emailProcessor;

    public ViewProcessor(EmailProcessor emailProcessor) {
        this.emailProcessor = emailProcessor;
    }

    public void showLoginWindow() {
        System.out.println("showLoginWindow invoked.");

        BaseController controller = new LoginWindowController(emailProcessor, this, FXMLFile.LoginWindow.toString());

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.ViewPackage + controller.getFxmlName()));
        loader.setController(controller);

        Parent parent;

        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

}
