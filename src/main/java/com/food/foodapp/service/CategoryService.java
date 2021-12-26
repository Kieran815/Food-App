package com.food.foodapp.service;

import com.food.foodapp.controller.CategoryController;
import com.food.foodapp.exception.InformationExistException;
import com.food.foodapp.exception.InformationNotFoundException;
import com.food.foodapp.model.Category;
import com.food.foodapp.model.Recipe;
import com.food.foodapp.repository.CategoryRepository;
import com.food.foodapp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

// Service Workers contain the business logic that gets passed to the controller methods
@Service
public class CategoryService {

  //  create instance of Category Repository to call JpaRepository methods
  private CategoryRepository categoryRepository;

  // create instance of Recipe Repository to call JpaRepository methods
  private RecipeRepository recipeRepository;

  //  logger class for output: (Similar to `console.log`)
  private static final Logger LOGGER = Logger.getLogger(CategoryService.class.getName());
  //  ********************** ^^^^ THIS ^^^^ **********************

  @Autowired
  // creates a bean (single instance) of the category Repository to call and link the methods to the controller
  public void setCategoryRepository(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  // creates java bean (single instance)
  @Autowired
  public void setRecipeRepository(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  // *** GET ALL CATEGORIES
  public List<Category> getCategories() {
    LOGGER.info("Retrieving Categories From Service...");
    return categoryRepository.findAll();
  }

  //  *** GET SPECIFIC CATEGORY BY ID
//  passed variable will point to an item in the database
  public Optional getCategory(Long categoryId) {
    LOGGER.info("Getting Category From Service...");
    Optional category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
      return category;
    } else {
      throw new InformationNotFoundException("Category with Id: " + categoryId + " Not Found... :(");
    }
  }

  //  *** CREATE CATEGORY
//  http://localhost:9092/api/categories/
  public Category createCategory(Category categoryObject) {
    LOGGER.info("Creating Category....");
    Category category = categoryRepository.findByName(categoryObject.getName());
    if(category != null) {
      throw new InformationExistException("Category Name Already In Use: " + category.getName());
    } else {
      return categoryRepository.save(categoryObject);
    }
  }

  //  category id comes from 'getCategory'
  // passed from `PutMapping` into method as `value`, along with `body`
  public Category updateCategory(Long categoryId, Category categoryObject) {
    LOGGER.info("calling updateCategory method from Service");
    Optional<Category> category = categoryRepository.findById(categoryId);
    // findById
    if (category.isPresent()) {
      // check the category name match with the category name in the DB
      if (categoryObject.getName().equals(category.get().getName())) {
        LOGGER.warning("category name is equal to database object name");
        throw new InformationExistException("category " + category.get().getName() + " Already Exists");
      } else {
        // find the category and update with new information
        Category updateCategory = categoryRepository.findById(categoryId).get();
        updateCategory.setName(categoryObject.getName());
        updateCategory.setDescription(categoryObject.getDescription());
        return categoryRepository.save(updateCategory);
      }
    } else {
      throw new InformationNotFoundException("category with id " + categoryId + " Not Found... :(");
    }
  }

  //  Delete Method to delete category by id
  public Optional<Category> deleteCategory(Long categoryId) {
    LOGGER.info("calling deleteCategory method from Service");
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
//      **** DeleteById() ****
      categoryRepository.deleteById(categoryId);
      return category;
    } else {
      throw new InformationNotFoundException("category with id " + categoryId + " Not Found... :(");
    }
  }

  // ***************************** RECIPES *****************************

  // create single recipe and add to category
  public Recipe createCategoryRecipe(Long categoryId, Recipe recipeObject) {
    LOGGER.info("Calling createCategoryRecipe from Service");
    try {
      Optional category = categoryRepository.findById(categoryId);
      recipeObject.setCategory((Category) category.get()); // add category to recipe
      return recipeRepository.save(recipeObject);
    } catch (NoSuchElementException e) { // if category object does not exist
      throw new InformationNotFoundException("Category " + categoryId + " Not Found... :(");
    }
  }

  public List<Recipe> getCategoryRecipes(Long categoryId) {
    LOGGER.info("Getting Recipe List from Service...");
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
      return recipeRepository.findByCategoryId(categoryId);
    } else {
      throw new InformationNotFoundException("Category " + categoryId + " Not Found... :(");
    }
  }

  public Recipe getCategoryRecipe(Long categoryId, Long recipeId) {
    LOGGER.info("Getting Category Recipe from Service...");
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
      // find category, then stream to find (filter) recipe by recipeId and grab the first instance.
      Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId).stream().filter(p-> p.getId().equals(recipeId)).findFirst();
      if (recipe.isEmpty()) {
        throw new InformationNotFoundException("Recipe with ID " + recipeId + " Not Found... :(");
      } else {
        return recipe.get();
      }
    } else {
      throw new InformationNotFoundException("Category " + categoryId + " Not Found... :(");
    }
  }

  public Recipe updateCategoryRecipe(Long categoryId, Long recipeId, Recipe recipeObject) {
    LOGGER.info("Updating Recipe from Service...");
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
      Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId).stream().filter(p-> p.getId().equals(recipeId)).findFirst();
      if (recipe.isEmpty()) {
        throw new InformationNotFoundException("Recipe with ID " + recipeId + " Not Found... :(");
      } else {
        Recipe updateRecipe = recipeRepository.findById(recipeId).get();
        updateRecipe.setId(recipeObject.getId());
        updateRecipe.setName(recipeObject.getName());
        updateRecipe.setTime(recipeObject.getTime());
        updateRecipe.setPortions(recipeObject.getPortions());
        updateRecipe.setIngredients(recipeObject.getIngredients());
        updateRecipe.setSteps(recipeObject.getSteps());
        updateRecipe.setPublic(recipeObject.getPublic());
        return recipeRepository.save(updateRecipe);
      }
    } else {
      throw new InformationNotFoundException("Category " + categoryId + " Not Found... :(");
    }
  }

  //  Delete Method to delete category by id
  public Optional<Category> deleteRecipe(Long categoryId, Long recipeId) {
    LOGGER.info("calling deleteCategory method from Service");
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isPresent()) {
      Optional<Recipe> recipe = recipeRepository.findByCategoryId(categoryId).stream().filter(p-> p.getId().equals(recipeId)).findFirst();
      if (recipe.isEmpty()) {
        throw new InformationNotFoundException("Recipe with ID " + recipeId + " Not Found... :(");
      } else {
        recipeRepository.deleteById(recipeId);
      }
    } else {
      throw new InformationNotFoundException("Category " + categoryId + " Not Found... :(");
    }
    return category;
  }

}
