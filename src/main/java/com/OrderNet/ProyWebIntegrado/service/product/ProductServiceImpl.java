package com.OrderNet.ProyWebIntegrado.service.product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.OrderNet.ProyWebIntegrado.dto.category.CategoryDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductUpdateDTO;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Category;
import com.OrderNet.ProyWebIntegrado.persistence.model.entities.Product;
import com.OrderNet.ProyWebIntegrado.persistence.repository.CategoryRepository;
import com.OrderNet.ProyWebIntegrado.persistence.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  private CategoryDTO toCategoryDTO(Category category) {
    return CategoryDTO.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
  }

  private ProductDTO toDTO(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .available(product.getAvailable())
        .categoryDTO(toCategoryDTO(product.getCategory()))
        .build();
  }

  @Override
  public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
    Category category = categoryRepository.findById(productCreateDTO.getCategoryId())
        .orElseThrow(
            () -> new NoSuchElementException("Categoría no encontrada con ID: " + productCreateDTO.getCategoryId()));

    Product newProduct = Product.builder()
        .name(productCreateDTO.getName())
        .description(productCreateDTO.getDescription())
        .price(productCreateDTO.getPrice())
        .available(productCreateDTO.getAvailable())
        .category(category)
        .build();

    Product savedProduct = productRepository.save(newProduct);
    return toDTO(savedProduct);
  }

  @Override
  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + id));
    return toDTO(product);
  }

  @Override
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ProductDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + id));

    if (productUpdateDTO.getName() != null) {
      product.setName(productUpdateDTO.getName());
    }

    if (productUpdateDTO.getDescription() != null) {
      product.setDescription(productUpdateDTO.getDescription());
    }

    if (productUpdateDTO.getPrice() != null) {
      product.setPrice(productUpdateDTO.getPrice());
    }

    if (productUpdateDTO.getCategoryId() != null) {
      Category category = categoryRepository.findById(productUpdateDTO.getCategoryId())
          .orElseThrow(
              () -> new NoSuchElementException("Categoría no encontrada con ID: " + productUpdateDTO.getCategoryId()));

      product.setCategory(category);
    }

    if (productUpdateDTO.getAvailable() != null) {
      product.setAvailable(productUpdateDTO.getAvailable());
    }

    Product updatedProduct = productRepository.save(product);
    return toDTO(updatedProduct);
  }

  @Override
  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new NoSuchElementException("No existe un producto con ID: " + id);
    }
    productRepository.deleteById(id);
  }

}
