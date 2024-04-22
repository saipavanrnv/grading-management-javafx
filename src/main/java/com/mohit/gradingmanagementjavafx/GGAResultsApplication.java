package com.mohit.gradingmanagementjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.URL;

@SpringBootApplication
@ComponentScan("com.mohit.gradingmanagementjavafx.controller")
public class GGAResultsApplication extends Application {

  private double x = 0 ;
  private double y = 0;

  public static URL geturl(String documentName) {
    return GGAResultsApplication.class.getResource(documentName);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent fxmlLoader = FXMLLoader.load(GGAResultsApplication.class.getResource("FXMLDocument.fxml"));
    Scene scene = new Scene(fxmlLoader);
    fxmlLoader.setOnMousePressed((MouseEvent event) ->{
      x = event.getSceneX();
      y = event.getSceneY();
    });

    fxmlLoader.setOnMouseDragged((MouseEvent event) ->{

      stage.setX(event.getScreenX() - x);
      stage.setY(event.getScreenY() - y);

      stage.setOpacity(0.8);

    });

    fxmlLoader.setOnMouseReleased((MouseEvent event) ->{
      stage.setOpacity(1);
    });

    stage.initStyle(StageStyle.TRANSPARENT);

    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch();
  }
}