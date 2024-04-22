package com.mohit.gradingmanagementjavafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyController implements Initializable {

  @FXML
  private WebView webView;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    WebEngine webEngine = webView.getEngine();
    webEngine.load(getClass().getResource("/login.html").toExternalForm());
    // Adjust WebView size to fit content
    webEngine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
      if (newDoc != null) {
        Number width = (Number) webEngine.executeScript(
            "Math.max(document.documentElement.scrollWidth, document.body.scrollWidth, document.documentElement.offsetWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.body.clientWidth)");
        Number height = (Number) webEngine.executeScript(
            "Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.offsetHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.body.clientHeight)");
        webView.setPrefSize(width.doubleValue(), height.doubleValue());
      }
    });
  }
}
