package com.zubaku.processors;

import com.zubaku.controller.BaseController;
import com.zubaku.controller.LoginWindowController;
import com.zubaku.controller.MainWindowController;
import com.zubaku.controller.OptionsWindowController;
import com.zubaku.utils.FXMLFile;
import com.zubaku.utils.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewProcessor {
    private static final Logger LOGGER = Logger.getLogger(ViewProcessor.class.getName());

    private final EmailProcessor emailProcessor;

    public ViewProcessor(EmailProcessor emailProcessor) {
        this.emailProcessor = emailProcessor;
    }

    public void showLoginWindow() {
        System.out.println("showLoginWindow invoked.");

        BaseController controller = new LoginWindowController(emailProcessor, this, FXMLFile.LoginWindow.toString());
        initializeStage(controller);
    }

    public void showMainWindow() {
        System.out.println("showMainWindow invoked.");

        BaseController controller = new MainWindowController(emailProcessor, this, FXMLFile.MainWindow.toString());
        initializeStage(controller);
    }

    public void showOptionsWindow() {
        System.out.println("showOptionsWindow invoked.");

        BaseController controller = new OptionsWindowController(emailProcessor, this, FXMLFile.OptionsWindow.toString());
        initializeStage(controller);
    }

    public void initializeStage(BaseController controller) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.ViewPackage + controller.getFxmlName()));
        loader.setController(controller);

        Parent parent;

        try {
            parent = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while loading parent", e);
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void closeStage(Stage stage){
        LOGGER.log(Level.INFO, "Closed the stage");
        stage.close();
    }

}
