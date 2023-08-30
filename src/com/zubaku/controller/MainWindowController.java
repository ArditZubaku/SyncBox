package com.zubaku.controller;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController{

    @FXML
    private TableView<?> emailTableView;

    @FXML
    private WebView emailWebView;

    @FXML
    private TreeView<?> emailsTreeView;

    public MainWindowController(EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
        super(emailProcessor, viewProcessor, fxmlName);
    }

    @FXML
    void optionsAction(ActionEvent event) {

    }

}