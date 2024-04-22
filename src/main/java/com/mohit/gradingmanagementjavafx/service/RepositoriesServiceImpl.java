package com.mohit.gradingmanagementjavafx.service;

import com.mohit.gradingmanagementjavafx.model.LoginDetails;
import com.mohit.gradingmanagementjavafx.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoriesServiceImpl {

  @Autowired
  private LoginRepository loginRepository;


  public Optional<LoginDetails> getDetails(String username, String password) {
    Optional<LoginDetails> loginDetails = loginRepository.findByEmailAndPassword(username, password);
    return loginDetails;
  }
}
