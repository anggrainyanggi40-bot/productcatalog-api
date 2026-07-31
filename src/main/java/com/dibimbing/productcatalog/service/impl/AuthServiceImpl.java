package com.dibimbing.productcatalog.service.impl;

import com.dibimbing.productcatalog.dto.request.LoginRequest;
import com.dibimbing.productcatalog.dto.request.RegisterRequest;
import com.dibimbing.productcatalog.dto.response.LoginResponse;
import com.dibimbing.productcatalog.dto.response.RegisterResponse;
import com.dibimbing.productcatalog.entity.User;
import com.dibimbing.productcatalog.enumeration.Role;
import com.dibimbing.productcatalog.repository.UserRepository;
import com.dibimbing.productcatalog.service.AuthService;
import com.dibimbing.productcatalog.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
        throw new RuntimeException("Username sudah digunakan");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email sudah digunakan");
        }

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
            .id(savedUser.getId())
            .username(savedUser.getUsername())
            .email(savedUser.getEmail())
            .role(savedUser.getRole().name())
            .build();
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
        );

        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .map(authority -> authority.replaceFirst("^ROLE_", ""))
            .orElseThrow();

        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow();

        String token = jwtService.generateToken(
            authentication.getName(),
            role,
            buildExtraClaims(user)
        );

        return LoginResponse.builder()
            .token(token)
            .tokenType("Bearer")
            .expiresInMs(jwtService.getExpirationMs())
            .username(authentication.getName())
            .role(role)
            .build();
    }
    private Map<String, Object> buildExtraClaims(User user) {

    Map<String, Object> claims = new HashMap<>();

    claims.put("id", user.getId());
    claims.put("username", user.getUsername());
    claims.put("email", user.getEmail());

    return claims;
    }
}