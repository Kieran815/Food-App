package com.food.foodapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String userName;

  @Column(unique = true)
  private String emailAddress;

  @Column
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  // one user can have only one profile
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id",referencedColumnName = "id")
  private UserProfile userProfile;

  // user can have more than one recipe
  @OneToMany(mappedBy = "user")
  @LazyCollection(LazyCollectionOption.FALSE) // FALSE prevents program from loading all related recipes
  private List<Recipe> recipeList;

  // user can have more than one category
  @OneToMany(mappedBy = "user")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Category> categoryList;

  public User() {}


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserProfile getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {
    this.userProfile = userProfile;
  }

  public List<Recipe> getRecipeList() {
    return recipeList;
  }

  public void setRecipeList(List<Recipe> recipeList) {
    this.recipeList = recipeList;
  }

  public List<Category> getCategoryList() {
    return categoryList;
  }

  public void setCategoryList(List<Category> categoryList) {
    this.categoryList = categoryList;
  }
}
