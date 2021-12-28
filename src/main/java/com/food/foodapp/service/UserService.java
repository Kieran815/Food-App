package com.food.foodapp.service;

import com.food.foodapp.exception.InformationExistException;
import com.food.foodapp.model.Request.LoginRequest;
import com.food.foodapp.model.Response.LoginResponse;
import com.food.foodapp.model.User;
import com.food.foodapp.repository.UserRepository;
import com.food.foodapp.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
  private UserRepository userRepository;

  private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils; // sends JWT token

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

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

  public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
    authenticationManager.authenticate( // this is where actual authentication takes place
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail()); // load credentials
    final String jwt = jwtUtils.generateToken(userDetails);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }



  public User findUserByEmailAddress(String email) {
    return userRepository.findUserByEmailAddress(email);
  }
}
