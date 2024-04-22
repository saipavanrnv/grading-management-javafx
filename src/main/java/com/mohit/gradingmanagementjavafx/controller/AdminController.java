package com.mohit.gradingmanagementjavafx.controller;

import com.mohit.gradingmanagementjavafx.GGAResultsApplication;
import com.mohit.gradingmanagementjavafx.model.LocalUsage;
import com.mohit.gradingmanagementjavafx.model.StudentDetails;
import com.mohit.gradingmanagementjavafx.model.TeacherDetails;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminController implements Initializable {

  @FXML
  private Button btn_home;

  @FXML
  private Button btn_teacher;

  @FXML
  private Button btn_student;

  @FXML
  private Button btn_logout;

  @FXML
  private AnchorPane topbar;

  @FXML
  private Button add_student;

  @FXML
  private Button add_teacher;

  @FXML
  private Circle circle;

  @FXML
  private TableColumn<StudentDetails, String> col_student_class;

  @FXML
  private TableColumn<StudentDetails, String> col_student_email;

  @FXML
  private TableColumn<StudentDetails, String> col_student_lastname;

  @FXML
  private TableColumn<TeacherDetails, String> col_teacher_class;

  @FXML
  private TableColumn<TeacherDetails, String> col_teacher_email;

  @FXML
  private TableColumn<TeacherDetails, String> col_teacher_lastname;

  @FXML
  private TableColumn<TeacherDetails, String> col_teacher_subject;

  @FXML
  private AnchorPane home_page;

  @FXML
  private AnchorPane navbar;

  @FXML
  private Button search_teacher;

  @FXML
  private ComboBox<String> student_class;

  @FXML
  private TextField student_email;

  @FXML
  private TextField student_first_name;

  @FXML
  private TextField student_last_name;

  @FXML
  private AnchorPane student_page;

  @FXML
  private PasswordField student_password;

  @FXML
  private TableView<StudentDetails> student_table_view;

  @FXML
  private ComboBox<String> teacher_class;

  @FXML
  private TextField teacher_email;

  @FXML
  private TextField teacher_first_name;

  @FXML
  private TextField teacher_last_name;

  @FXML
  private AnchorPane teacher_page;

  @FXML
  private PasswordField teacher_password;

  @FXML
  private ComboBox<String> teacher_subject;

  @FXML
  private TableView<TeacherDetails> teacher_table_view;

  @FXML
  private Label total_student;

  @FXML
  private AnchorPane total_student_card;

  @FXML
  private AnchorPane total_student_card1;

  @FXML
  private Label total_teachers;

  @FXML
  private Button update_student;

  @FXML
  private Label user;

  //    TOOLS FOR DATABASE
  private Connection connect;
  private Statement statement;
  private PreparedStatement prepare;
  private ResultSet result;

  private double x = 0;
  private double y = 0;

  @FXML
  void addStudent(ActionEvent event) {
    insertData("Student");
  }

  @FXML
  void addTeacher(ActionEvent event) {
    insertData("Teacher");
  }

  private void insertData(String role) {

    if (role.equalsIgnoreCase("Teacher")) {
      validatingAndInserting(role, teacher_first_name, teacher_last_name, teacher_email, teacher_password, teacher_class, teacher_subject);
    } else if (role.equalsIgnoreCase("Student")) {
      ComboBox<String> dummyComboBox = new ComboBox<>();
      dummyComboBox.setValue("None"); // Setting dummy subject comboBox cause we will not have subject in the student form.
      validatingAndInserting(role, student_first_name, student_last_name, student_email, student_password, student_class, dummyComboBox);
    }
  }

  private void validatingAndInserting(String role, TextField firstName, TextField lastName, TextField email,
                                      TextField password, ComboBox<String> classBelongTo, ComboBox<String> subject) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern emailPattern = Pattern.compile(emailRegex);

    String passwordRegex = "^(?=.*[!@#$%^&*()-+=])(?=.*[a-zA-Z]).{8,}$";
    Pattern passwordPattern = Pattern.compile(passwordRegex);

    System.out.println(subject.getSelectionModel().getSelectedItem());

    if (Stream.of(firstName.getText(), lastName.getText(), email.getText(), password.getText()).anyMatch(String::isBlank)
        || classBelongTo.getSelectionModel().isEmpty() || subject.getSelectionModel().getSelectedItem().isBlank()) {

      sendAlert(Alert.AlertType.ERROR, "Error Message", "Please provide all the fields");

    } else if (!emailPattern.matcher(email.getText()).matches()) {

      sendAlert(Alert.AlertType.ERROR, "Error Message", "EmailId is not valid");

    } else if (!passwordPattern.matcher(password.getText()).matches()) {

      sendAlert(Alert.AlertType.ERROR, "Error Message", "Password doesn't match the pattern :: Should be atleast 8 characters and Must contain one symbol");

    } else if (isDataExist(email)) {

      sendAlert(Alert.AlertType.ERROR, "Error Message", "Email already exists!!");

    } else {
      insertDetails(role);
    }
  }

  private boolean isDataExist(TextField email) {
    connect = database.connectDb();
    String emailCountSql = "select count(*) from tbl_login where email = ?";

    try {
      prepare = connect.prepareStatement(emailCountSql);
      prepare.setString(1, email.getText());
      result = prepare.executeQuery();

      if (result.next()) {
        int anInt = result.getInt(1);
        return anInt > 0;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  private void insertDetails(String role) {

    connect = database.connectDb();
    String loginInsertSql = "INSERT INTO tbl_login(`email`, `password`, `role`, `last_name`)  VALUES (?,?,?,?)";

    try {
      prepare = connect.prepareStatement(loginInsertSql);
      prepare.setString(1, role.equalsIgnoreCase("Teacher") ? teacher_email.getText()
          : student_email.getText());
      prepare.setString(2, role.equalsIgnoreCase("Teacher") ? teacher_password.getText()
          : student_password.getText());
      prepare.setString(3, role);
      prepare.setString(4, role.equalsIgnoreCase("Teacher") ? teacher_last_name.getText()
          : student_last_name.getText());

      int rowsAffected = prepare.executeUpdate(); // Insert to tbl_login

      if (rowsAffected > 0) {
        if (role.equalsIgnoreCase("Teacher")) {

          String teacherInsertSql = "INSERT INTO tbl_teacher(`email`, `first_name`, `last_name`, `class_belong_to`, `teaching_subject`)  VALUES (?,?,?,?,?)";

          try {
            prepare = connect.prepareStatement(teacherInsertSql);
            prepare.setString(1, teacher_email.getText());
            prepare.setString(2, teacher_first_name.getText());
            prepare.setString(3, teacher_last_name.getText());
            prepare.setString(4, teacher_class.getSelectionModel().getSelectedItem());
            prepare.setString(5, teacher_subject.getSelectionModel().getSelectedItem());

            int rowsAffected1 = prepare.executeUpdate(); // Insert to tbl_teacher

            if (rowsAffected1 > 0) {
              clearTeacherForm(); // Clearing the teacher form once it's inserted
              sendAlert(Alert.AlertType.INFORMATION, "Insert Successful", "Added the new Teacher");
              loadTeacherTable();
            }

          } catch (Exception e) {
            e.printStackTrace();
          }
        } else if (role.equalsIgnoreCase("Student")) {

          String studentInsertSql = "INSERT INTO tbl_student(`email`, `first_name`, `last_name`, `class_belong_to`, `exam_type`) " +
              " VALUES (?,?,?,?,?),(?,?,?,?,?),(?,?,?,?,?),(?,?,?,?,?)";

          try {
            prepare = connect.prepareStatement(studentInsertSql);

            int preparedStatement = 1;
            for (int i = 0; i < 4; i++) {
              prepare.setString(preparedStatement++, student_email.getText());
              prepare.setString(preparedStatement++, student_first_name.getText());
              prepare.setString(preparedStatement++, student_last_name.getText());
              prepare.setString(preparedStatement++, student_class.getSelectionModel().getSelectedItem());
              preparedStatement++;
            }
            prepare.setString(5, "Exam-I");
            prepare.setString(10, "Mid Exam");
            prepare.setString(15, "Exam-II");
            prepare.setString(20, "Final Exam");

            int rowsAffected1 = prepare.executeUpdate(); // Insert to tbl_student

            if (rowsAffected1 > 0) {
              clearStudentForm(); // Clearing the student form once it's inserted
              sendAlert(Alert.AlertType.INFORMATION, "Insert Successful", "Added the new Student");
              loadStudentTable();
            }

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void clearTeacherForm() {
    teacher_first_name.setText("");
    teacher_last_name.setText("");
    teacher_email.setText("");
    teacher_password.setText("");
    teacher_class.getSelectionModel().clearSelection();
    teacher_subject.getSelectionModel().clearSelection();
  }

  public void clearStudentForm() {
    student_first_name.setText("");
    student_last_name.setText("");
    student_email.setText("");
    student_password.setText("");
    student_class.getSelectionModel().clearSelection();
  }

  @FXML
  void changePage(ActionEvent event) throws IOException {

    if (btn_home.isFocused()) {
      home_page.setVisible(true);
      teacher_page.setVisible(false);
      student_page.setVisible(false);
      totalCounts(); // STATS right away!
    } else if (btn_teacher.isFocused()) {
      loadTeacherTable();
      home_page.setVisible(false);
      teacher_page.setVisible(true);
      student_page.setVisible(false);
    } else if (btn_student.isFocused()) {
      loadStudentTable();
      home_page.setVisible(false);
      teacher_page.setVisible(false);
      student_page.setVisible(true);
    } else if (btn_logout.isFocused()) {
      logoutAccount();
    }
  }

  private void totalCounts() {
    // Get the total Teachers/Students count
    connect = database.connectDb();
    total_teachers.setText(getCount("Teacher"));
    total_student.setText(getCount("Student"));
  }

  private String getCount(String role) {
    String countSql = "select count(email) from tbl_login where role = ?";
    try {
      prepare = connect.prepareStatement(countSql);
      prepare.setString(1, role);
      result = prepare.executeQuery();

      if (result.next()) {
        return result.getString("count(email)");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
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
  void searchTeacher(ActionEvent event) {

  }

  @FXML
  void selectData(MouseEvent event) {

  }

  @FXML
  void textfieldRecord() {

  }

  @FXML
  void updateStudent(ActionEvent event) {

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    account(); // Displays the lastName of user below the "Welcome"
    totalCounts(); // STATS right away!
    classComboBox(); // Dropdown menu for classes
    subjectComboBox();// Dropdown menu for subjects
  }

  private void loadTeacherTable() {
    connect = database.connectDb();

    String sql = "SELECT * FROM tbl_teacher";

    try {

      prepare = connect.prepareStatement(sql);

      result = prepare.executeQuery();

      ObservableList<TeacherDetails> teacherRecord = FXCollections.observableArrayList();

      while (result.next()) {

        TeacherDetails teacherData = new TeacherDetails(result.getString("id")
            , result.getString("first_name")
            , result.getString("last_name")
            , result.getString("email")
            , result.getString("class_belong_to")
            , result.getString("teaching_subject"));

        teacherRecord.addAll(teacherData);
      }

      if (!teacherRecord.isEmpty()) {
        // TO SHOW THE DATA TO TABLE VIEW
        col_teacher_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_teacher_subject.setCellValueFactory(new PropertyValueFactory<>("teachingSubject"));
        col_teacher_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_teacher_class.setCellValueFactory(new PropertyValueFactory<>("classBelongTo"));
      }

      teacher_table_view.setItems(teacherRecord);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadStudentTable() {
    connect = database.connectDb();

    String sql = "SELECT * FROM tbl_student";

    try {

      prepare = connect.prepareStatement(sql);

      result = prepare.executeQuery();

      ObservableList<StudentDetails> studentRecords = FXCollections.observableArrayList();

      while (result.next()) {

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

        studentRecords.addAll(containData);
      }

      if (!studentRecords.isEmpty()) {
        System.out.println(studentRecords);
        studentRecords = studentRecords.stream()
            .collect(Collectors.groupingBy(StudentDetails::getEmail))
            .values().stream()
            .map(list -> list.get(0))
            .collect(
                Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList
                ));
        // TO SHOW THE DATA TO TABLE VIEW
        col_student_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_student_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_student_class.setCellValueFactory(new PropertyValueFactory<>("classBelongTo"));
      }

      student_table_view.setItems(studentRecords);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void account() {
    user.setText(LocalUsage.lastName);
  }

  public void classComboBox() {
    List<String> list = new ArrayList<>(Arrays.asList("Grade-4", "Grade-5", "Grade-6", "Grade-7", "Grade-8", "Grade-9"));
    ObservableList<String> dataList = FXCollections.observableArrayList(list);
    teacher_class.setItems(dataList);
    student_class.setItems(dataList);
  }

  public void subjectComboBox() {
    List<String> list = new ArrayList<>(Arrays.asList("Maths", "Science", "Social", "Regional Language", "Computer Sciences"));
    ObservableList<String> dataList = FXCollections.observableArrayList(list);
    teacher_subject.setItems(dataList);
  }

  private void sendAlert(Alert.AlertType error, String title, String contentText) {
    Alert alert = new Alert(error);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(contentText);
    alert.showAndWait();
  }

}
