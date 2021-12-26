package com.food.foodapp.repository;

import com.food.foodapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

  // grab list of all recipes in category
  List<Recipe> findByCategoryId(Long categoryId);

}
