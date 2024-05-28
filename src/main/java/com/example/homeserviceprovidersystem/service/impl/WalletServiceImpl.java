package com.example.homeserviceprovidersystem.service.impl;

import com.example.homeserviceprovidersystem.dto.customer.CustomerRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.wallet.WalletResponse;
import com.example.homeserviceprovidersystem.entity.Customer;
import com.example.homeserviceprovidersystem.entity.Expert;
import com.example.homeserviceprovidersystem.entity.Wallet;
import com.example.homeserviceprovidersystem.mapper.WalletMapper;
import com.example.homeserviceprovidersystem.repositroy.WalletRepository;
import com.example.homeserviceprovidersystem.service.CustomerService;
import com.example.homeserviceprovidersystem.service.ExpertService;
import com.example.homeserviceprovidersystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final CustomerService customerService;
    private final ExpertService expertService;

    @Autowired
    public WalletServiceImpl(
            WalletRepository walletRepository,
            WalletMapper walletMapper,
            @Lazy CustomerService customerService,
            @Lazy ExpertService expertService) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.customerService = customerService;
        this.expertService = expertService;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public WalletResponse findWallet(CustomerRequestWithEmail request, String person) {
        if (person.equals("customer")) {
            Customer customer = customerService.findByEmail(request.getCustomerEmail());
            return walletMapper.walletToWalletResponse(customer.getWallet());
        } else {
            Expert expert = expertService.findByEmail(request.getCustomerEmail());
            return walletMapper.walletToWalletResponse(expert.getWallet());
        }
    }
}
