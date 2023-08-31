package com.example.investmentmanager.mappers;

import com.example.investmentmanager.entities.CryptoCurrency;
import com.example.investmentmanager.model.CryptoCurrencyDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CryptoCurrencyMapper {

    CryptoCurrency cryptoDtoToCrypto(CryptoCurrencyDTO cryptoCurrencyDto);
    CryptoCurrencyDTO cryptoToCryptoDto(CryptoCurrency cryptoCurrency);
}
