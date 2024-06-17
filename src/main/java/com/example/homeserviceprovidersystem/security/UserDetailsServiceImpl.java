package com.example.homeserviceprovidersystem.security;

import com.example.homeserviceprovidersystem.customeException.CustomEntityNotFoundException;
import com.example.homeserviceprovidersystem.entity.Admin;
import com.example.homeserviceprovidersystem.entity.Customer;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.service.AdminService;
import com.example.homeserviceprovidersystem.service.CustomerService;
import com.example.homeserviceprovidersystem.service.ExpertService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {
    public static String userType = "";
    final AdminService adminService;
    final CustomerService customerService;
    final ExpertService expertService;

    @Autowired
    public UserDetailsServiceImpl(
            @Lazy AdminService adminService,
            @Lazy CustomerService customerService,
            @Lazy ExpertService expertService) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.expertService = expertService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userType == null || userType.isEmpty()) {
            throw new CustomEntityNotFoundException("User type is not specified");
        }
        switch (userType) {
            case "ADMIN" -> {
                Admin admin = adminService.findByEmail(username);
                return new AdminDetails(admin);
            }
            case "CUSTOMER" -> {
                Customer customer = customerService.findByEmail(username);
                return new CustomerDetails(customer);
            }
            case "EXPERT" -> {
                Expert expert = expertService.findByEmail(username);
                return new ExpertDetails(expert);
            }
            default -> throw new CustomEntityNotFoundException("User type is not specified");
        }
    }
}
