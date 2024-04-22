package com.mohit.gradingmanagementjavafx.repository;


import com.mohit.gradingmanagementjavafx.model.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginDetails, Long> {

  Optional<LoginDetails> findByEmailAndPassword(String email, String password);

//  List<UserForm> findAllByClassBelongToAndExamType(String classBelongTo, String examType);
//
//  List<UserForm> findAllByEmailAndPassword(String username, String password);

}
