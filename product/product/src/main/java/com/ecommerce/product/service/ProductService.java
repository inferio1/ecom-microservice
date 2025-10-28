package com.ecommerce.product.service;


import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product=new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct=productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }
    private void updateProductFromRequest(Product product,ProductRequest productRequest)
    {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageURL(productRequest.getImageURL());
    }

    private ProductResponse mapToProductResponse(Product product)
    {

        ProductResponse response=new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setActive(product.getActive());
        response.setImageURL(product.getImageURL());
        return response;

    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest)
    {
        return productRepository.findById(id).map(existingProduct->{updateProductFromRequest(existingProduct,productRequest);
                Product savedProduct=productRepository.save(existingProduct);
        return mapToProductResponse(savedProduct);});
    }

    public List<ProductResponse> getAllProducts()
    {
        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id)
    {
       return productRepository.findById(id).
               map(product->{product.setActive(false);
               productRepository.save(product);
               return true;}).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword)
    {
       return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(String id) {
        return productRepository.findByIdAndActiveTrue(Long.valueOf(id)).map(this::mapToProductResponse);
    }
}
