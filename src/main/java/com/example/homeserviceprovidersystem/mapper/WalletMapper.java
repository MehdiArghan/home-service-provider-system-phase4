package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.wallet.WalletResponse;
import com.example.homeserviceprovidersystem.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    WalletResponse walletToWalletResponse(Wallet wallet);
}
