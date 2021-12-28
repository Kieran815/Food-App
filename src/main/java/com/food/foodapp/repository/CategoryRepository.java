package com.food.foodapp.repository;

import com.food.foodapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Link Repository to bring in methods from Java Spring via `JpaRepository`
public interface CategoryRepository extends JpaRepository<Category, Long> {
//  use Jpa to do Dynamic Queries of the database
  Category findByName(String categoryName); // retrieve categories from user

  List<Category> findByUserId(Long userId);

  Category findByUserIdAndName(Long userId, String categoryName);
}
