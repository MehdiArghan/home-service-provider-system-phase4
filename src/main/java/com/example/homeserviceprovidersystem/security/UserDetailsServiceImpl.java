package com.example.homeserviceprovidersystem.security;

import com.example.homeserviceprovidersystem.entity.Customer;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.service.CustomerService;
import com.example.homeserviceprovidersystem.service.ExpertService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {
    public static String userType = "";
    final CustomerService customerService;
    final ExpertService expertService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userType.equals("CUSTOMER")) {
            Customer customer = customerService.findByEmail(username);
            return new CustomerDetails(customer);
        } else if (userType.equals("EXPERT")) {
            Expert expert = expertService.findByEmail(username);
            return new ExpertDetails(expert);
        } else {
            return null;
        }
    }
}
