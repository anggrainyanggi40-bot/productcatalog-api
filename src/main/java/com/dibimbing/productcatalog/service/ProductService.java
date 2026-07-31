package com.dibimbing.productcatalog.service;

import com.dibimbing.productcatalog.dto.request.ProductRequest;
import com.dibimbing.productcatalog.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll();

    ProductResponse getById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

}