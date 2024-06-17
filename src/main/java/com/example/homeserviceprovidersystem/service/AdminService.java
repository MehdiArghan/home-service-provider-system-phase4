package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.entity.Admin;

public interface AdminService {
    Admin findByEmail(String email);
}
