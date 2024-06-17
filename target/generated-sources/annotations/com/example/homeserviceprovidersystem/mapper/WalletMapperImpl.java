package com.example.homeserviceprovidersystem.mapper;

import com.example.homeserviceprovidersystem.dto.wallet.WalletResponse;
import com.example.homeserviceprovidersystem.entity.Wallet;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-17T13:11:01+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletResponse walletToWalletResponse(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletResponse walletResponse = new WalletResponse();

        walletResponse.setPrice( wallet.getPrice() );

        return walletResponse;
    }
}
