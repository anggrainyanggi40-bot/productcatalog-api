package com.dibimbing.productcatalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterResponse {

    private Long id;

    private String username;

    private String email;

    private String role;
}