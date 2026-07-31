package com.dibimbing.productcatalog.controller;

import com.dibimbing.productcatalog.dto.request.LoginRequest;
import com.dibimbing.productcatalog.dto.request.RegisterRequest;
import com.dibimbing.productcatalog.dto.response.ApiResponse;
import com.dibimbing.productcatalog.dto.response.LoginResponse;
import com.dibimbing.productcatalog.dto.response.RegisterResponse;
import com.dibimbing.productcatalog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(
            @RequestBody RegisterRequest request) {

        return ApiResponse.success(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ApiResponse.success(
                authService.login(request)
        );
    }
}