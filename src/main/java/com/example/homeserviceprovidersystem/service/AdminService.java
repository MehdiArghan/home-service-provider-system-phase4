package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.entity.Admin;
import com.example.homeserviceprovidersystem.security.AuthenticationRequest;
import com.example.homeserviceprovidersystem.security.AuthenticationResponse;

public interface AdminService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    Admin findByEmail(String email);
}
