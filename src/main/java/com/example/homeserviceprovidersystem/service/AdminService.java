package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.password.ChangePasswordRequest;
import com.example.homeserviceprovidersystem.entity.Admin;
import com.example.homeserviceprovidersystem.security.AuthenticationRequest;
import com.example.homeserviceprovidersystem.security.AuthenticationResponse;

public interface AdminService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    String changePassword(ChangePasswordRequest request);

    Admin findByEmail(String email);
}
