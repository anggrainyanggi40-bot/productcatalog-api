package com.dibimbing.productcatalog.config;

import com.dibimbing.productcatalog.entity.Product;
import com.dibimbing.productcatalog.entity.User;
import com.dibimbing.productcatalog.enumeration.Role;
import com.dibimbing.productcatalog.repository.ProductRepository;
import com.dibimbing.productcatalog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        seedUsers();
        seedProducts();

    }

    private void seedUsers() {

        if (userRepository.count() > 0) {
            return;
        }

        User admin = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        User user = User.builder()
                .username("user")
                .email("user@gmail.com")
                .password(passwordEncoder.encode("user123"))
                .role(Role.USER)
                .build();

        userRepository.save(admin);
        userRepository.save(user);

    }

    private void seedProducts() {

        if (productRepository.count() > 0) {
            return;
        }

        productRepository.save(
                Product.builder()
                        .name("Laptop ASUS")
                        .description("Laptop ASUS Vivobook")
                        .price(new BigDecimal("8500000"))
                        .stock(10)
                        .build());

        productRepository.save(
                Product.builder()
                        .name("Mouse Logitech")
                        .description("Wireless Mouse")
                        .price(new BigDecimal("250000"))
                        .stock(25)
                        .build());

        productRepository.save(
                Product.builder()
                        .name("Mechanical Keyboard")
                        .description("RGB Keyboard")
                        .price(new BigDecimal("700000"))
                        .stock(15)
                        .build());

    }
}