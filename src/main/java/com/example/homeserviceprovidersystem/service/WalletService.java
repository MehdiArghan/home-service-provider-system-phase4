package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.customer.CustomerRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.wallet.WalletResponse;
import com.example.homeserviceprovidersystem.entity.Wallet;

public interface WalletService {
    Wallet save(Wallet wallet);

    WalletResponse findWallet(CustomerRequestWithEmail request, String person);
}
