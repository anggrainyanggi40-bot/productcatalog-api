package com.dibimbing.productcatalog.service.impl;

import com.dibimbing.productcatalog.dto.request.ProductRequest;
import com.dibimbing.productcatalog.dto.response.ProductResponse;
import com.dibimbing.productcatalog.entity.Product;
import com.dibimbing.productcatalog.repository.ProductRepository;
import com.dibimbing.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Cacheable("products")
    @Override
    public List<ProductResponse> getAll() {
    return productRepository.findByDeletedFalse()
            .stream()
            .map(this::toResponse)
            .toList();
    }

@Cacheable(value = "products", key = "#id")
@Override
public ProductResponse getById(Long id) {

    Product product = productRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Product tidak ditemukan"));

    return toResponse(product);
}
@CacheEvict(value = "products", allEntries = true)
@Override
public ProductResponse create(ProductRequest request) {

    Product product = Product.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .stock(request.getStock())
            .build();

    Product savedProduct = productRepository.save(product);

    return toResponse(savedProduct);
}

@CachePut(value = "products", key = "#id")
@Override
public ProductResponse update(Long id, ProductRequest request) {

    Product product = productRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Product tidak ditemukan"));

    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setStock(request.getStock());

    Product updatedProduct = productRepository.save(product);

    return toResponse(updatedProduct);
}

@CacheEvict(value = "products", allEntries = true)
@Override
public void delete(Long id) {

    Product product = productRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Product tidak ditemukan"));

    product.setDeleted(true);
    product.setDeletedAt(Instant.now());

    productRepository.save(product);
}

private ProductResponse toResponse(Product product) {

    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .build();
}
}