package com.OrderNet.ProyWebIntegrado.service.product;

import java.util.List;

import com.OrderNet.ProyWebIntegrado.dto.product.ProductCreateDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductDTO;
import com.OrderNet.ProyWebIntegrado.dto.product.ProductUpdateDTO;

public interface ProductService {
  ProductDTO createProduct(ProductCreateDTO productCreateDTO);

  ProductDTO getProductById(Long id);

  List<ProductDTO> getAllProducts();

  ProductDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO);

  void deleteProduct(Long id);
}
