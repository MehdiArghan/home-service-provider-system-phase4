package com.example.homeserviceprovidersystem.service;

import com.example.homeserviceprovidersystem.dto.person.PersonRequestWithEmail;
import com.example.homeserviceprovidersystem.dto.wallet.WalletResponse;
import com.example.homeserviceprovidersystem.entity.Wallet;

public interface WalletService {
    Wallet save(Wallet wallet);

    WalletResponse findWallet(PersonRequestWithEmail request, String personType);
}
