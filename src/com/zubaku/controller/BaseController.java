package com.zubaku.controller;

import com.zubaku.processors.EmailProcessor;
import com.zubaku.processors.ViewProcessor;

public abstract class BaseController {

    private EmailProcessor emailProcessor;
    private ViewProcessor viewProcessor;
    private String fxmlName;

    public BaseController(EmailProcessor emailProcessor, ViewProcessor viewProcessor, String fxmlName) {
        this.emailProcessor = emailProcessor;
        this.viewProcessor = viewProcessor;
        this.fxmlName = fxmlName;
    }

    public EmailProcessor getEmailProcessor() {
        return emailProcessor;
    }

    public void setEmailProcessor(EmailProcessor emailProcessor) {
        this.emailProcessor = emailProcessor;
    }

    public ViewProcessor getViewProcessor() {
        return viewProcessor;
    }

    public void setViewProcessor(ViewProcessor viewProcessor) {
        this.viewProcessor = viewProcessor;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public void setFxmlName(String fxmlName) {
        this.fxmlName = fxmlName;
    }
}
