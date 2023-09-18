module JAVAFX.PROJEKTI {
  requires javafx.fxml;
  requires javafx.controls;
  requires javafx.graphics;
  requires javafx.web;
  requires java.logging;
  requires java.mail;
  requires activation;

  opens com.zubaku.controllers;
  opens com.zubaku.application;
  opens com.zubaku.processors;
  opens com.zubaku.models;
    opens com.zubaku.utils;
}