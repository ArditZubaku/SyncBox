module com.zubaku {
  requires javafx.fxml;
  requires javafx.controls;
  requires javafx.graphics;
  requires javafx.web;
  requires java.logging;
  requires jakarta.mail;
  requires jakarta.activation;
  requires java.desktop;

  opens com.zubaku.controllers;
  opens com.zubaku.application;
  opens com.zubaku.processors;
  opens com.zubaku.models;
  opens com.zubaku.utils;
}
