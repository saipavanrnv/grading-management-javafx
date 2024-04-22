package com.mohit.gradingmanagementjavafx.controller;

import com.mohit.gradingmanagementjavafx.GGAResultsApplication;
import com.mohit.gradingmanagementjavafx.model.LocalUsage;
import com.mohit.gradingmanagementjavafx.model.StudentDetails;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class TeacherController implements Initializable {

  @FXML
  private AnchorPane student_editing_pane;

  @FXML
  private AnchorPane warning_message;

  @FXML
  private Button search_student;

  @FXML
  private Button update_records;

  @FXML
  private AnchorPane records_page;

  @FXML
  private Button btn_logout;

  @FXML
  private Button btn_overview;

  @FXML
  private Button btn_records;

  @FXML
  private Circle circle;

  @FXML
  private TableColumn<?, ?> col_cs;

  @FXML
  private TableColumn<?, ?> col_lastname;

  @FXML
  private TableColumn<?, ?> col_mat;

  @FXML
  private TableColumn<?, ?> col_rl;

  @FXML
  private TableColumn<?, ?> col_sci;

  @FXML
  private TableColumn<?, ?> col_soc;

  @FXML
  private TableColumn<?, ?> col_id;

  @FXML
  private TableColumn<?,?> col_class;


  @FXML
  private Label form_cs;

  @FXML
  private TextField form_cs_field;

  @FXML
  private Label form_last_name;

  @FXML
  private TextField form_last_name_field;

  @FXML
  private Label form_mat;

  @FXML
  private TextField form_mat_field;

  @FXML
  private Label form_rl;

  @FXML
  private TextField form_rl_field;

  @FXML
  private Label form_sci;

  @FXML
  private TextField form_sci_field;

  @FXML
  private Label form_soc;

  @FXML
  private TextField form_soc_field;

  @FXML
  private AnchorPane navbar;

  @FXML
  private AnchorPane overview_page;

  @FXML
  private Button search_teacher;

  @FXML
  private Button search_teacher1;

  @FXML
  private ComboBox<String> student_class;

  @FXML
  private ComboBox<String> student_examtype;

  @FXML
  private TextField student_last_name;

  @FXML
  private TableView<StudentDetails> student_table_view;

  @FXML
  private AnchorPane topbar;

  @FXML
  private Label user;

  private String studentId;

  // TOOLS FOR DATABASE
  private Connection connect;
  private Statement statement;
  private PreparedStatement prepare;
  private ResultSet result;

  private double x = 0;
  private double y = 0;

  @FXML
  void changePage(ActionEvent event) throws IOException {
    if (btn_overview.isFocused()) {
      overview_page.setVisible(true);
      records_page.setVisible(false);
    } else if (btn_records.isFocused()) {
      overview_page.setVisible(false);
      records_page.setVisible(true);
    } else if (btn_logout.isFocused()) {
      logoutAccount();
    }
  }

  private void logoutAccount() throws IOException {

    btn_logout.getScene().getWindow().hide();

    URL resource = GGAResultsApplication.geturl("FXMLDocument.fxml");
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
  void selectData() {
    int num = student_table_view.getSelectionModel().getSelectedIndex();

    StudentDetails data1 = student_table_view.getSelectionModel().getSelectedItem();

    if(num-1 < -1)
      return;

    form_last_name_field.setText(data1.getLastName());

    form_mat_field.setText(data1.getMaths());
    form_cs_field.setText(data1.getComputerSciences());
    form_rl_field.setText(data1.getRegionalLanguage());
    form_sci_field.setText(data1.getScience());
    form_soc_field.setText(data1.getSocial());
    studentId = String.valueOf(data1.getId());

    if (!data1.getClassBelongTo().equalsIgnoreCase(teacherClass)) {
      warning_message.setVisible(true);
      if (teacherSubject.equalsIgnoreCase("Maths")) {
        form_mat_field.setEditable(true);
        form_mat_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      } else if (teacherSubject.equalsIgnoreCase("Computer Sciences")) {
        form_cs_field.setEditable(true);
        form_cs_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      } else if (teacherSubject.equalsIgnoreCase("Regional Language")) {
        form_rl_field.setEditable(true);
        form_rl_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      } else if (teacherSubject.equalsIgnoreCase("Science")) {
        form_sci_field.setEditable(true);
        form_sci_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      } else if (teacherSubject.equalsIgnoreCase("Social")) {
        form_soc_field.setEditable(true);
        form_soc_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      }
    } else {
      form_mat_field.setEditable(true);
      form_mat_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      form_cs_field.setEditable(true);
      form_cs_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      form_rl_field.setEditable(true);
      form_rl_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      form_soc_field.setEditable(true);
      form_soc_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
      form_sci_field.setEditable(true);
      form_sci_field.setStyle("-fx-border-color:linear-gradient(to bottom left, #517ab5, #ae44a5)");
    }
    student_editing_pane.setVisible(true);
  }

  @FXML
  void textfieldRecord() {

  }

  @FXML
  void searchStudent() {

    if (student_examtype.getSelectionModel().isEmpty() || student_class.getSelectionModel().isEmpty()) {
      sendAlert(Alert.AlertType.ERROR, "Error Message", "Please select both fields");
    } else {
      warning_message.setVisible(false);
      student_editing_pane.setVisible(false);

      ObservableList<StudentDetails> studentRecord = getStudentRecords();

      if (studentRecord.isEmpty()) {
        sendAlert(Alert.AlertType.ERROR, "Error Message", "No data found");
      } else {
        // TO SHOW THE DATA TO TABLE VIEW
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_rl.setCellValueFactory(new PropertyValueFactory<>("regionalLanguage"));
        col_cs.setCellValueFactory(new PropertyValueFactory<>("computerSciences"));
        col_mat.setCellValueFactory(new PropertyValueFactory<>("maths"));
        col_sci.setCellValueFactory(new PropertyValueFactory<>("science"));
        col_soc.setCellValueFactory(new PropertyValueFactory<>("social"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_class.setCellValueFactory(new PropertyValueFactory<>("classBelongTo"));
      }
      student_table_view.setItems(studentRecord);
    }
  }

  private ObservableList<StudentDetails> getStudentRecords() {
    connect = database.connectDb();

    ObservableList<StudentDetails> listData = FXCollections.observableArrayList();

    String sql = "SELECT * FROM tbl_student WHERE class_belong_to = ? and exam_type = ?";

    try {

      prepare = connect.prepareStatement(sql);
      prepare.setString(1, student_class.getSelectionModel().getSelectedItem());
      prepare.setString(2, student_examtype.getSelectionModel().getSelectedItem());

      result = prepare.executeQuery();

      while(result.next()){

        StudentDetails containData = new StudentDetails(result.getString("id")
            , result.getString("last_name")
            , result.getString("email")
            , result.getString("class_belong_to")
            , result.getString("exam_type")
            , result.getString("maths")
            , result.getString("science")
            , result.getString("social")
            , result.getString("regional_language")
            , result.getString("computer_sciences"));

        listData.addAll(containData);

      }

    } catch(Exception e) {
      e.printStackTrace();
    }

    return listData;
  }

  @FXML
  void updateStudentRecords(ActionEvent actionEvent) {

    connect = database.connectDb();
    String updateStudentSql = "UPDATE `tbl_student` SET `computer_sciences` = ?, `maths` = ?, `regional_language` = ?, `science` = ?, `social` = ? WHERE (`id` = ?)";


    try {

      prepare = connect.prepareStatement(updateStudentSql);

      Stream.of(form_cs_field.getText(), form_mat_field.getText(), form_rl_field.getText(),
          form_sci_field.getText(), form_soc_field.getText())
          .filter(s -> (s != null && !s.isBlank()))
          .forEach(this::isFormFieldValid);

      prepare.setString(1, form_cs_field.getText());
      prepare.setString(2, form_mat_field.getText());
      prepare.setString(3, form_rl_field.getText());
      prepare.setString(4, form_sci_field.getText());
      prepare.setString(5, form_soc_field.getText());
      prepare.setString(6, studentId);

      int rowsAffected1 = prepare.executeUpdate();

      if (rowsAffected1 > 0) {
        clearEditingStudentForm();
        sendAlert(Alert.AlertType.INFORMATION, "Update Successful", "Updated the student records");
        searchStudent();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void isFormFieldValid(String s) {
    try {
      float f = Float.parseFloat(s);
      if (f < 0.0 || f > 4.0) {
        sendAlert(Alert.AlertType.ERROR, "Error Message", "Grades can't be less than 0.0 and greater than 4.0");
        throw new RuntimeException("Grades can't be less than 0.0 and greater than 4.0");
      }
    } catch (NumberFormatException e) {
      sendAlert(Alert.AlertType.ERROR, "Error Message", "Grades should only be a decimal formatted value");
      throw new RuntimeException("Grades should only be a decimal formatted value");
    }
  }

  private void clearEditingStudentForm() {
    form_cs_field.setText("");
    form_mat_field.setText("");
    form_rl_field.setText("");
    form_sci_field.setText("");
    form_soc_field.setText("");
    form_last_name_field.setText("");
    student_editing_pane.setVisible(false);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    account();
    comboBox();
  }

  private String teacherSubject;
  private String teacherClass;


  private void account() {

    connect = database.connectDb();

    String sql = "SELECT * FROM tbl_teacher WHERE email = ?";

    try {

      prepare = connect.prepareStatement(sql);
      prepare.setString(1, LocalUsage.email);

      result = prepare.executeQuery();

      if (result.next()) {
        teacherClass = result.getString("class_belong_to");
        teacherSubject = result.getString("teaching_subject");
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
    user.setText(LocalUsage.lastName);
  }

  private void comboBox() {
    List<String> classList = new ArrayList<>(Arrays.asList("Grade-4", "Grade-5", "Grade-6", "Grade-7", "Grade-8", "Grade-9"));
    ObservableList<String> classObservable = FXCollections.observableArrayList(classList);
    student_class.setItems(classObservable);

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
