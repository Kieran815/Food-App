package com.food.foodapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.util.List;

// `Entity` is a Java Class that represents a Table
@Entity
@Table(name = "categories") // tells database what to name table
// `model` represents a table in a database
// table name should reflect controller and model names
public class Category {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY) // sets up a serial value for id
  private Long id; // Primary Key, annotated as @Id and @Column

  @Column
  private String name;

  @Column
  private String description;

//  recipe list from Recipe class
  @OneToMany(mappedBy = "category", orphanRemoval = true)
//  below tells app to NOT collect all related values
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Recipe> recipeList;

  @ManyToOne // many objects belong to this one instance
  @JoinColumn(name = "user_id")
  @JsonIgnore // ignore data from user info
  private User user;


  public Category() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Recipe> getRecipeList() {
    return recipeList;
  }

  public void setRecipeList(List<Recipe> recipeList) {
    this.recipeList = recipeList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

