package com.food.foodapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exception for Info Already Exists confilcts
@ResponseStatus(HttpStatus.CONFLICT)
// create a class that extends an Exception class
public class InformationExistException extends RuntimeException {
  public InformationExistException(String message) {
    super(message);
  }
}
