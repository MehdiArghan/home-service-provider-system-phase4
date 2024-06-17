package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.customer.CustomerRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerSummaryRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Customer;
import com.example.homeserviceprovidersystem.security.AuthenticationRequest;
import com.example.homeserviceprovidersystem.security.AuthenticationResponse;

import java.util.List;

public interface CustomerService {
    CustomerSummaryResponse save(CustomerRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    Customer findById(Long id);

    Customer findByEmail(String email);

    List<CustomerSummaryResponse> findCustomersByDynamicSearch(CustomerSummaryRequest request);

    String verifyToken(String token);
}
