package com.mohit.gradingmanagementjavafx.controller;

import com.mohit.gradingmanagementjavafx.HelloApplication;
import com.mohit.gradingmanagementjavafx.model.LocalUsage;
import com.mohit.gradingmanagementjavafx.model.SubjectGrade;
import com.mohit.gradingmanagementjavafx.repository.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class StudentController implements Initializable {

  @FXML
  private TableColumn<SubjectGrade, String> col_subject;

  @FXML
  private TableColumn<SubjectGrade, String> col_grade;

  @FXML
  private TableView<SubjectGrade> student_record_tableview;

  @FXML
  private Button btn_logout;

  @FXML
  private Circle circle;

  @FXML
  private Label form_last_name;

  @FXML
  private Label form_last_name1;

  @FXML
  private Label form_last_name11;

  @FXML
  private Button search_student;

  @FXML
  private Label student_class;

  @FXML
  private ComboBox<String > student_examtype;

  @FXML
  private Label student_full_name;

  @FXML
  private Label student_full_name11;

  @FXML
  private AnchorPane topbar;

  // TOOLS FOR DATABASE
  private Connection connect;
  private Statement statement;
  private PreparedStatement prepare;
  private ResultSet result;

  private String mathsGrades;
  private String scienceGrades;
  private String socialGrades;
  private String regionalLanguageGrades;
  private String computerSciencesGrades;

  private double x = 0;
  private double y = 0;

  @FXML
  void changePage(ActionEvent event) throws IOException {
    if (btn_logout.isFocused()) {
      logoutAccount();
    }
  }

  private void logoutAccount() throws IOException {

    btn_logout.getScene().getWindow().hide();

    URL resource = HelloApplication.geturl("FXMLDocument.fxml");
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
  }


  @FXML
  void classComboBox(ActionEvent event) {

  }

  @FXML
  void exit(ActionEvent event) {
    System.exit(0);
  }

  @FXML
  void searchStudent(ActionEvent event) {

    student_record_tableview.setVisible(false);
    student_record_tableview.getItems().clear();

    connect = database.connectDb();

    String sql = "SELECT * FROM tbl_student WHERE email = ? and exam_type = ?";

    try {

      prepare = connect.prepareStatement(sql);
      prepare.setString(1, LocalUsage.email);
      prepare.setString(2, student_examtype.getSelectionModel().getSelectedItem());

      result = prepare.executeQuery();

      if (result.next() && Stream.of(result.getString("maths"),result.getString("science"),
              result.getString("social"), result.getString("regional_language"), result.getString("computer_sciences"))
          .anyMatch(s -> s != null && !s.isBlank())) {

        mathsGrades = result.getString("maths");
        scienceGrades = result.getString("science");
        socialGrades = result.getString("social");
        regionalLanguageGrades = result.getString("regional_language");
        computerSciencesGrades = result.getString("computer_sciences");

        ObservableList<SubjectGrade> data1 = FXCollections.observableArrayList();

        data1.add(new SubjectGrade("Regional Language", regionalLanguageGrades));
        data1.add(new SubjectGrade("Computer Sciences", computerSciencesGrades));
        data1.add(new SubjectGrade("Math", mathsGrades));
        data1.add(new SubjectGrade("Science", scienceGrades));
        data1.add(new SubjectGrade("Social", socialGrades));
        data1.add(new SubjectGrade("", ""));

        col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Add a row for average grades
        OptionalDouble average = Stream.of(mathsGrades, scienceGrades, socialGrades, regionalLanguageGrades, computerSciencesGrades)
            .filter(s -> s != null && !s.isBlank()).map(Float::parseFloat).mapToDouble(Float::doubleValue).average();
        if (average.isPresent()) {
          DecimalFormat df = new DecimalFormat("#.#");
          data1.add(new SubjectGrade("Average", String.valueOf(df.format(average.getAsDouble()))));
        }

        student_record_tableview.setItems(data1);

        double tableViewHeight = 24 * 8.2;

        student_record_tableview.setPrefHeight(tableViewHeight);
        student_record_tableview.setVisible(true);
      } else {
        sendAlert(Alert.AlertType.ERROR, "Error Message", "No data found");
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void textfieldRecord(MouseEvent event) {

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    account();
    comboBox();
  }

  public void account() {

    connect = database.connectDb();

    String sql = "SELECT * FROM tbl_student WHERE email = ?";

    try {

      prepare = connect.prepareStatement(sql);
      prepare.setString(1, LocalUsage.email);

      result = prepare.executeQuery();

      if (result.next()) {
        student_full_name.setText(result.getString("first_name") + ", " + result.getString("last_name"));
        student_class.setText(result.getString("class_belong_to"));

      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void comboBox() {

    List<String> examList = new ArrayList<>(Arrays.asList("Exam-I", "Mid Exam", "Exam-II", "Final Exam"));
    ObservableList<String> examObservable = FXCollections.observableArrayList(examList);
    student_examtype.setItems(examObservable);
  }

  private void sendAlert(Alert.AlertType error, String title, String contentText) {
    Alert alert = new Alert(error);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(contentText);
    alert.showAndWait();
  }
}
