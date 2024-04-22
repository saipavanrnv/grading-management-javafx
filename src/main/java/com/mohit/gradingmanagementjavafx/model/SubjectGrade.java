package com.mohit.gradingmanagementjavafx.model;

public class SubjectGrade {

  private String subject;
  private String grade;

  public String getSubject() {
    return subject;
  }

  public String getGrade() {
    return grade;
  }

  public SubjectGrade(String subject, String grade) {
    this.subject = subject;
    this.grade = grade;
  }
}
