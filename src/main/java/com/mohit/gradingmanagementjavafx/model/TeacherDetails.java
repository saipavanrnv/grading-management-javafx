package com.mohit.gradingmanagementjavafx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDetails {

  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String classBelongTo;
  private String teachingSubject;
}
