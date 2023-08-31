package com.zubaku.processors;

import com.zubaku.controller.BaseController;
import com.zubaku.controller.LoginWindowController;
import com.zubaku.controller.MainWindowController;
import com.zubaku.controller.OptionsWindowController;
import com.zubaku.utils.ColorTheme;
import com.zubaku.utils.FXMLFile;
import com.zubaku.utils.FontSize;
import com.zubaku.utils.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewProcessor {
    private static final Logger LOGGER = Logger.getLogger(ViewProcessor.class.getName());

    private final EmailProcessor emailProcessor;
    private final List<Stage> activeStages;
    // View options handling:
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;
    public ViewProcessor(EmailProcessor emailProcessor) {
        this.emailProcessor = emailProcessor;
        this.activeStages = new ArrayList<>();
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
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

        activeStages.add(stage);

    }

    public void closeStage(Stage stage) {
        LOGGER.log(Level.INFO, "Closed the stage");
        stage.close();
        activeStages.remove(stage);

    }

    public void updateStyles() {
        // In JavaFX styles are applied to scenes => we need list of all active scenese

        for (Stage stage : activeStages) {
            Scene scene = stage.getScene();
            // TODO: handle the CSS
        }

    }
}
