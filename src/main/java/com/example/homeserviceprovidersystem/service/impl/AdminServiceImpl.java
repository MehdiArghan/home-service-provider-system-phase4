package com.example.homeserviceprovidersystem.service.impl;

import com.example.homeserviceprovidersystem.customeException.CustomEntityNotFoundException;
import com.example.homeserviceprovidersystem.entity.Admin;
import com.example.homeserviceprovidersystem.repositroy.AdminRepository;
import com.example.homeserviceprovidersystem.security.*;
import com.example.homeserviceprovidersystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AdminServiceImpl(
            AdminRepository adminRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UserDetailsServiceImpl.userType = "ADMIN";
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Admin admin = findByEmail(request.getUsername());
        String token = jwtService.generateToken(new AdminDetails(admin), "ADMIN");
        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException("Admin with this email was not found"));
    }
}