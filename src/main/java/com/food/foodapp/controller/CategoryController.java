package com.food.foodapp.controller;

import com.food.foodapp.model.Category;
import com.food.foodapp.model.Recipe;
import com.food.foodapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// import to create RESTful API
@RestController
// set default prefix with Request Mapping:
@RequestMapping(path = "/api")

public class CategoryController {
  // annotate to set up GET mapping
  // in dev, url is http://localhost:9092/api/helloworld
//  @GetMapping("/helloworld")
//  public String helloWorld() {
//    return "Hello World";
//  }


  private CategoryService categoryService;

//  logger class for output: (Similar to `console.log`)
  private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());
  //  ********************** ^^^^ THIS ^^^^ **********************

  @Autowired
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

// *** GET ALL CATEGORIES
  @GetMapping("/categories")
  public List<Category> getCategories() {
    LOGGER.info("Retrieving Categories...");
    return categoryService.getCategories();
  }

//  *** GET SPECIFIC CATEGORY BY ID
//  pass variables inside curley braces in path url
  @GetMapping(path = "/categories/{categoryId}")
//  passed variable will point to an item in the database
  public Optional getCategory(@PathVariable Long categoryId) {
    LOGGER.info("Getting Category...");
    return categoryService.getCategory(categoryId);
  }

//  *** CREATE CATEGORY
//  http://localhost:9092/api/categories/
  @PostMapping(path = "/categories")
  public Category createCategory(@RequestBody Category categoryObject) {
    LOGGER.info("Creating Category....");
    return categoryService.createCategory(categoryObject);
  }

//  category id comes from 'getCategory'
  // passed from `PutMapping` into method as `value`, along with `body`
  @PutMapping(path = "/categories/{categoryId}")
  public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject) {
    LOGGER.info("calling updateCategory method from controller");
    return categoryService.updateCategory(categoryId, categoryObject);
  }

  //  Delete Method to delete category by id
  @DeleteMapping("/categories/{categoryId}")
  public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
    LOGGER.info("calling deleteCategory method from controller");
    return categoryService.deleteCategory(categoryId);
  }

//  Create Recipe
//  http"/localhost:9092/api/categories/1/recipes
  @PostMapping("/categories/{categoryId}/recipes")
  public Recipe createCategoryRecipe(  // path to category
    @PathVariable(value = "categoryId") Long categoryId,
    @RequestBody Recipe recipeObject
  ) {
    LOGGER.info("Calling Create Category Recipe from Controller");
    return categoryService.createCategoryRecipe(categoryId, recipeObject);
  }

  // Get Recipe by ID from DB
  //  http://localhost:9092/api/categories/{categoryId}/recipes/{recipeId}
  @GetMapping("/categories/{categoryId}/recipes/{recipeId}")
  public Recipe getCategoryRecipe(
      @PathVariable(value = "categoryId") Long categoryId,
      @PathVariable(value = "recipeId") Long recipeId
  ) {
    LOGGER.info("Calling Get Category Recipe from Controller");
    return categoryService.getCategoryRecipe(categoryId, recipeId);
  }


  @GetMapping("/categories/{categoryId}/recipes")
  public List<Recipe> getCategoryRecipes(@PathVariable(value = "categoryId") Long categoryId) {
    LOGGER.info("Calling all Recipes from Category " + categoryId + "..." );
    return categoryService.getCategoryRecipes(categoryId);
  }

  // passed from `PutMapping` into method as `value`, along with `body`
  @PutMapping(path = "/categories/{categoryId}/recipes/{recipeId}")
  public Recipe updateCategoryRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId, @RequestBody Recipe recipeObject) {
    LOGGER.info("calling updateCategory method from controller");
    return categoryService.updateCategoryRecipe(categoryId, recipeId, recipeObject);
  }


  //  Delete Method to delete recipe by id
  @DeleteMapping("/categories/{categoryId}/recipes/{recipeId}")
  public Optional<Category> deleteRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId) {
    LOGGER.info("calling deleteCategory method from controller");
    return categoryService.deleteRecipe(categoryId, recipeId);
  }
}

