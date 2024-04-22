package com.mohit.gradingmanagementjavafx.model;

public class StudentDetails {

  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String classBelongTo;
  private String examType;
  private String maths;
  private String science;
  private String social;
  private String regionalLanguage;
  private String computerSciences;

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return "StudentDetails{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", classBelongTo='" + classBelongTo + '\'' +
        ", examType='" + examType + '\'' +
        ", maths='" + maths + '\'' +
        ", science='" + science + '\'' +
        ", social='" + social + '\'' +
        ", regionalLanguage='" + regionalLanguage + '\'' +
        ", computerSciences='" + computerSciences + '\'' +
        '}';
  }

  public StudentDetails(String id, String lastName, String email, String classBelongTo, String examType, String maths, String science, String social, String regionalLanguage,
                        String computerSciences) {
    this.id = Long.valueOf(id);
    this.lastName = lastName;
    this.email = email;
    this.classBelongTo = classBelongTo;
    this.examType = examType;
    this.maths = maths;
    this.science = science;
    this.social = social;
    this.regionalLanguage = regionalLanguage;
    this.computerSciences = computerSciences;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getClassBelongTo() {
    return classBelongTo;
  }

  public String getExamType() {
    return examType;
  }

  public String getMaths() {
    return maths;
  }

  public String getScience() {
    return science;
  }

  public String getSocial() {
    return social;
  }

  public String getRegionalLanguage() {
    return regionalLanguage;
  }

  public String getComputerSciences() {
    return computerSciences;
  }
}
