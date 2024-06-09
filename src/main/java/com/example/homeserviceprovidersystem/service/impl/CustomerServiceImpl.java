package com.example.homeserviceprovidersystem.service.impl;

import com.example.homeserviceprovidersystem.customeException.CustomBadRequestException;
import com.example.homeserviceprovidersystem.customeException.CustomEntityNotFoundException;
import com.example.homeserviceprovidersystem.dto.customer.CustomerRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerSummaryRequest;
import com.example.homeserviceprovidersystem.dto.customer.CustomerSummaryResponse;
import com.example.homeserviceprovidersystem.entity.Customer;
import com.example.homeserviceprovidersystem.entity.Wallet;
import com.example.homeserviceprovidersystem.mapper.CustomerMapper;
import com.example.homeserviceprovidersystem.repositroy.CustomerRepository;
import com.example.homeserviceprovidersystem.service.CustomerService;
import com.example.homeserviceprovidersystem.service.EmailService;
import com.example.homeserviceprovidersystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletService walletService;
    private final CustomerMapper customerMapper;
    private final EmailService emailService;
    private String token;
    private Customer customer;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            WalletService walletService,
            CustomerMapper customerMapper,
            EmailService emailService) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
        this.customerMapper = customerMapper;
        this.emailService = emailService;
    }

    @Override
    public CustomerSummaryResponse save(CustomerRequest request) {
        customerRepository.findByEmail(request.getEmail()).ifPresent(existingCustomer -> {
            throw new CustomBadRequestException("Email already exists");
        });
        Customer customer = customerMapper.customerRequestTocustomer(request);
        String token = UUID.randomUUID().toString();
        emailService.sendEmail(customer.getEmail(), "Welcome to our Service",
                "Thank you for registering! Please click the following link to verify your email: "
                        + "http://localhost:8080/customer/verifyToken?token=" + token);
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        this.token = token;
        this.customer = customer;
        return customerMapper.customerToCustomerSummaryResponse(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("customer with this id was not found"));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException("customer with this email was not found"));
    }

    @Override
    public List<CustomerSummaryResponse> findCustomersByDynamicSearch(CustomerSummaryRequest request) {
        Specification<Customer> specification = Specification.where(null);
        boolean isRequestValid = false;

        if (isNotEmpty(request.getFirstName())) {
            specification = specification.and(hasFirstName(request.getFirstName()));
            isRequestValid = true;
        }
        if (isNotEmpty(request.getLastName())) {
            specification = specification.and(hasLastName(request.getLastName()));
            isRequestValid = true;
        }
        if (isNotEmpty(request.getEmail())) {
            specification = specification.and(hasEmail(request.getEmail()));
            isRequestValid = true;
        }
        if (!isRequestValid) {
            throw new CustomBadRequestException("There is no result");
        }

        List<Customer> customers = customerRepository.findAll(specification);
        if (customers.isEmpty()) {
            throw new CustomBadRequestException("There is no result");
        } else {
            return customers.stream().map(customerMapper::customerToCustomerSummaryResponse).toList();
        }
    }

    private boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private Specification<Customer> hasFirstName(String firstName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
    }

    private Specification<Customer> hasLastName(String lastName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
    }

    private Specification<Customer> hasEmail(String email) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%"));
    }

    @Override
    public String verifyToken(String token) {
        if (token.equals(this.token)) {
            this.customer.setWallet(walletService.save(new Wallet(2000.0)));
            customerRepository.save(this.customer);
            this.token = null;
            this.customer = null;
            return "successfulRegistration";
        } else {
            return "registrationFailed";
        }
    }
}
