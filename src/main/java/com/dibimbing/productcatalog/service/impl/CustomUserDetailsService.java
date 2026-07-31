package com.dibimbing.productcatalog.service.impl;

import com.dibimbing.productcatalog.entity.User;
import com.dibimbing.productcatalog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User tidak ditemukan"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        AuthorityUtils.createAuthorityList(
                                "ROLE_" + user.getRole().name()
                        )
                )
                .disabled(!user.isEnabled())
                .build();
    }
}