package com.dibimbing.productcatalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private String tokenType;

    private long expiresInMs;

    private String username;

    private String role;

}