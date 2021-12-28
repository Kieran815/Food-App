package com.food.foodapp.controller;

import com.food.foodapp.model.Request.LoginRequest;
import com.food.foodapp.model.User;
import com.food.foodapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/auth/users")

public class UserController {
  private UserService userService;
  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public User createUser(@RequestBody User userObject) {
    LOGGER.info("Calling createUser from Controller.");
    return userService.createUser(userObject);
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) { // pulls `username` and `password` from `LoginRequest` class in `Model/Request`
    return userService.loginUser(loginRequest);
  }

}


//tables interconnected
//create repo
//create service
//add pom.xml dep
// dev properties line add
//work on sec package
//  my user detail
//  user detail service
//  security configurer
//usercontroller