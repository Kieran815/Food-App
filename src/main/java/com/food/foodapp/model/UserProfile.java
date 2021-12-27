package com.food.foodapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class UserProfile {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String profileDescription;

  @JsonIgnore // tells program to ignore data from imported class (user)
  @OneToOne(mappedBy = "userProfile") // sets up one to one relationship based on user profile
  private User user;

  public UserProfile() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getProfileDescription() {
    return profileDescription;
  }

  public void setProfileDescription(String profileDescription) {
    this.profileDescription = profileDescription;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
