package com.mohit.gradingmanagementjavafx.controller;

import com.mohit.gradingmanagementjavafx.HelloApplication;
import com.mohit.gradingmanagementjavafx.model.LocalUsage;
import com.mohit.gradingmanagementjavafx.repository.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {


  @FXML
  private AnchorPane login_form;

  @FXML
  private TextField username;

  @FXML
  private PasswordField password;

  @FXML
  private Button login_btn;

  //    TOOLS FOR DATABASE
  private Connection connect;
  private Statement statement;
  private PreparedStatement prepare;
  private ResultSet result;


  private double x = 0;
  private double y = 0;

  public void exit(){
      System.exit(0);
  }

  public void textfieldDesign() {
    if (username.isFocused()) {
        username.setStyle("-fx-background-color:#fff; -fx-border-width:2px");
        password.setStyle("-fx-background-color:transparent; -fx-border-width:1px");
    } else if (password.isFocused()) {
        username.setStyle("-fx-background-color:transparent; -fx-border-width:1px");
        password.setStyle("-fx-background-color:#fff; -fx-border-width:2px");
    }
  }

  public void login() {

      connect = database.connectDb();

      String sql = "SELECT * FROM tbl_login WHERE email = ? and password = ?";

      try {

          prepare = connect.prepareStatement(sql);
          prepare.setString(1, username.getText());
          prepare.setString(2, password.getText());

          result = prepare.executeQuery();

          if (result.next()) {

              String role = result.getString("role");

              URL resource = null;
              if (role.equalsIgnoreCase("Admin")) {
                  resource = HelloApplication.geturl("Admin-view.fxml");
              } else if (role.equalsIgnoreCase("Teacher")) {
                  resource = HelloApplication.geturl("Teacher-view.fxml");
              } else if (role.equalsIgnoreCase("Student")) {
                  resource = HelloApplication.geturl("Student-view.fxml");
              }

              LocalUsage.lastName = result.getString("last_name");
              LocalUsage.email = result.getString("email");
              LocalUsage.role = role;

              login_btn.getScene().getWindow().hide();

              Parent root = FXMLLoader.load(resource);
              Scene scene = new Scene(root);
              Stage stage = new Stage();

              root.setOnMousePressed((MouseEvent event) -> {
                  x = event.getSceneX();
                  y = event.getSceneY();
              });

              root.setOnMouseDragged((MouseEvent event) -> {
                  stage.setX(event.getScreenX() - x);
                  stage.setY(event.getScreenY() - y);

                  stage.setOpacity(0.8);
              });

              root.setOnMouseReleased((MouseEvent event) -> {
                  stage.setOpacity(1);
              });

              stage.initStyle(StageStyle.TRANSPARENT);

              stage.setScene(scene);
              stage.show();
          } else {

              Alert alert = new Alert(AlertType.ERROR);

              alert.setTitle("Error Message");
              alert.setHeaderText(null);
              alert.setContentText("Wrong Username/Password!");
              alert.showAndWait();
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }
}
