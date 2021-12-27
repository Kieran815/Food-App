package com.food.foodapp.repository;

import com.food.foodapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//  to register a new user/email
  boolean existsByEmailAddress(String userEmailAddress);

//  to login
  User findUserByEmailAddress(String userEmailAddress);
}
