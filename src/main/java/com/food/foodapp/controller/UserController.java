package com.food.foodapp.controller;

import com.food.foodapp.model.User;
import com.food.foodapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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