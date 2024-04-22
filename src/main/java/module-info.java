module com.mohit.gradingmanagementjavafx {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.bootstrapfx.core;
  requires com.almasb.fxgl.all;
  requires static lombok;
  requires jakarta.persistence;
  requires spring.context;
  requires spring.data.jpa;
  requires spring.boot.autoconfigure;
  requires spring.beans;
  requires spring.core;
  requires spring.web;
  requires mysql.connector.j;

  opens com.mohit.gradingmanagementjavafx to javafx.fxml;
  exports com.mohit.gradingmanagementjavafx;
  exports com.mohit.gradingmanagementjavafx.controller;
  opens com.mohit.gradingmanagementjavafx.controller to javafx.fxml;
  opens com.mohit.gradingmanagementjavafx.model to javafx.base;
}