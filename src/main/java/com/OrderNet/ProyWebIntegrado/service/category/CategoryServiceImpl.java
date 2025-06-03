package com.OrderNet.ProyWebIntegrado.service.category;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.category.CategoryDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Category;
import com.OrderNet.ProyWebIntegrado.persistence.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private CategoryDTO toDTO(Category category) {
    return CategoryDTO.builder().id(category.getId())
        .name(category.getName())
        .build();
  }

  @Override
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category newCategory = Category.builder().name(categoryDTO.getName()).build();

    Category savedCategory = categoryRepository.save(newCategory);
    return toDTO(savedCategory);
  }

  @Override
  public CategoryDTO getCategoryById(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Categoria no encontrada con la ID: " + id));
    return toDTO(category);
  }

  @Override
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Categoria no encontrada con la ID: " + id));

    if (categoryDTO.getName() != null) {
      category.setName(categoryDTO.getName());
    }

    Category updateCategory = categoryRepository.save(category);
    return toDTO(updateCategory);
  }

  @Override
  public void deleteCategory(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new NoSuchElementException("No existe una categoria con la ID: " + id);
    }
    categoryRepository.deleteById(id);
  }

}
