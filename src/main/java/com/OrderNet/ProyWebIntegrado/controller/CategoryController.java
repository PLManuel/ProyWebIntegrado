package com.OrderNet.ProyWebIntegrado.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrderNet.ProyWebIntegrado.dto.category.CategoryDTO;
import com.OrderNet.ProyWebIntegrado.service.category.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping("/create")
  public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
    CategoryDTO category = categoryService.getCategoryById(id);
    return ResponseEntity.ok(category);
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    List<CategoryDTO> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
      @Valid @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
    return ResponseEntity.ok(updatedCategory);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
