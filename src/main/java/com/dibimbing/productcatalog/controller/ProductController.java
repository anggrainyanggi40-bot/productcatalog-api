package com.dibimbing.productcatalog.controller;

import com.dibimbing.productcatalog.dto.request.ProductRequest;
import com.dibimbing.productcatalog.dto.response.ApiResponse;
import com.dibimbing.productcatalog.dto.response.ProductResponse;
import com.dibimbing.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.success(productService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(productService.getById(id));
    }

    @PostMapping
    public ApiResponse<ProductResponse> create(
            @RequestBody ProductRequest request) {

        return ApiResponse.success(productService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        return ApiResponse.success(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {

        productService.delete(id);

        return ApiResponse.success("Product berhasil dihapus", null);
    }
}