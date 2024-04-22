package com.mohit.gradingmanagementjavafx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetails {

  private Long id;

  private String email;
  private String lastName;
  private String password;
  private String role;
}
