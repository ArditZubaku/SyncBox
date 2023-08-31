package com.zubaku.controller;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.utils.ColorTheme;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    public OptionsWindowController(EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
        super(emailProcessor, viewProcessor, fxmlName);
    }

    @FXML
    void applyButtonAction() {

    }

    @FXML
    void cancelButtonAction() {

    }

    // Setting the fields value right after creation
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupThemePicker();
    }

    private void setupThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        // Default value:
        themePicker.setValue(viewProcessor.getColorTheme());
    }
}
