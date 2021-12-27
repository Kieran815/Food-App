package com.food.foodapp.service;

import com.food.foodapp.exception.InformationExistException;
import com.food.foodapp.model.User;
import com.food.foodapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
  private UserRepository userRepository;

  private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired // create a bean
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User userObject) {
    LOGGER.info("Calling `createUser` method from User Service");
    if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
      userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
      return userRepository.save(userObject);
    } else {
      throw new InformationExistException("Email Address already in use: " + userObject.getEmailAddress());
    }
  }

  public User findUserByEmailAddress(String email) {
    return userRepository.findUserByEmailAddress(email);
  }
}
