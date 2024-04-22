package com.mohit.gradingmanagementjavafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class HelloController {
  @FXML
  public TextField usernameField;
  @FXML
  public PasswordField passwordField;

  @FXML
  private Text statusText;

  @FXML
  private CheckBox showPasswordCheckbox;

  @FXML
  private void initialize() {

//      showPasswordCheckbox.setOnAction(event -> {
//        passwordField.setVisible(!showPasswordCheckbox.isSelected());
//        // Set the managed property to true/false so that it still occupies the space when hidden
//        passwordField.setManaged(!showPasswordCheckbox.isSelected());
//      });
  }
}