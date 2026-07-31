package com.dibimbing.productcatalog.service;

import com.dibimbing.productcatalog.dto.request.LoginRequest;
import com.dibimbing.productcatalog.dto.request.RegisterRequest;
import com.dibimbing.productcatalog.dto.response.LoginResponse;
import com.dibimbing.productcatalog.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}