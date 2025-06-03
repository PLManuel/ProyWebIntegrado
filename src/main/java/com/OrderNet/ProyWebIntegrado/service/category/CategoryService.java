package com.OrderNet.ProyWebIntegrado.service.category;

import java.util.List;

import com.OrderNet.ProyWebIntegrado.dto.category.CategoryDTO;

public interface CategoryService {
  CategoryDTO createCategory(CategoryDTO categoryDTO);

  CategoryDTO getCategoryById(Long id);

  List<CategoryDTO> getAllCategories();

  CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

  void deleteCategory(Long id);
}
