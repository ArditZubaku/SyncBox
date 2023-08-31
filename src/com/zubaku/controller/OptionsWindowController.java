package com.zubaku.controller;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import com.zubaku.utils.ColorTheme;
import com.zubaku.utils.FontSize;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
        // Get the theme
        viewProcessor.setColorTheme(themePicker.getValue());
        // Get the font size
        viewProcessor.setFontSize(FontSize.values()[(int) fontSizePicker.getValue()]);
        // Update CSS styles
        viewProcessor.updateStyles();

    }

    @FXML
    void cancelButtonAction() {
        // Get the stage
        Stage stage = (Stage) fontSizePicker.getScene().getWindow();
        viewProcessor.closeStage(stage);
    }

    // Setting the fields value right after creation
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupThemePicker();
        setupSizePicker();
    }

    private void setupThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        // Default value:
        themePicker.setValue(viewProcessor.getColorTheme());
    }

    private void setupSizePicker() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length - 1);
        fontSizePicker.setValue(viewProcessor.getFontSize().ordinal());
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setShowTickLabels(true);
        // Showing enums values instead of numbers
        fontSizePicker.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double object) {
                // Initializing the value
                int i = object.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });
    }
}
